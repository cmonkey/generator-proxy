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

        val json = classOf[JSON].getMethod("toJSONString", classOf[Any]).invoke(this, proxyInfo)

        val generatorFile = host + port + ".json"

        println(json)

        val output = new FileOutputStream(new File(generatorFile))
        output.write(json.toString.getBytes)
        output.close()
      }
    })
  }

}
