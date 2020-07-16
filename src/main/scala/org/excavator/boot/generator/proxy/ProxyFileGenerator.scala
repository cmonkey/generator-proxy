package org.excavator.boot.generator.proxy

import java.io.{File, FileOutputStream}
import java.nio.file.{Files, Paths}

import com.alibaba.fastjson.JSON

object ProxyFileGenerator{
  def main(args: Array[String]): Unit = {
    val file = args(0)

    val lines = Files.readAllLines(Paths.get(file))

    lines.forEach(line => {
      val elems = line.split(" ")
      if (elems.nonEmpty) {
        val host = elems(1).trim
        val port = elems(2).trim
        val password = elems(3).trim
        val algorithm = elems(4).trim

        val proxyInfo = ProxyInfo(host, port.toInt, password, algorithm)

        var json = ""
        try {
          val jsonProxyInfo = classOf[JSON].getMethod("toJSONString", classOf[Any]).invoke(this, proxyInfo)
          json = jsonProxyInfo.toString
        }catch {
          case ex:Throwable => {
            println(s"parse proxyInfo to JSON failed = ${ex} Prepared manually splicing json")
            val builder = new StringBuilder
            builder.append("{")
            builder.append("\"").append("server").append("\"").append(":").append("\"").append(proxyInfo.getServer).append("\",")
            builder.append("\"").append("server_port").append("\"").append(":").append("\"").append(proxyInfo.getServer_port).append("\",")
            builder.append("\"").append("method").append("\"").append(":").append("\"").append(proxyInfo.getMethod).append("\",")
            builder.append("\"").append("password").append("\"").append(":").append("\"").append(proxyInfo.getPassword).append("\",")
            builder.append("\"").append("local_address").append("\"").append(":").append("\"").append(proxyInfo.getLocal_address).append("\",")
            builder.append("\"").append("local_port").append("\"").append(":").append("\"").append(proxyInfo.getLocal_port).append("\"")
            builder.append("}")

            json = builder.toString()

          }
        }

        val generatorFile = host + port + ".json"

        println(json)

        val output = new FileOutputStream(new File(generatorFile))
        output.write(json.getBytes)
        output.close()
      }
    })
  }

}
