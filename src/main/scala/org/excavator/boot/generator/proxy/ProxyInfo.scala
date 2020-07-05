package org.excavator.boot.generator.proxy

import scala.beans.BeanProperty

case class ProxyInfo(@BeanProperty server:String,
                     @BeanProperty server_port:Int,
                     @BeanProperty password:String,
                     @BeanProperty method:String,
                     @BeanProperty local_address:String="127.0.0.1",
                     @BeanProperty local_port:Int = 1080
                    )
