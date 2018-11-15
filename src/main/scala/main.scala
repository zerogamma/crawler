package com.agileengine

import com.agileengine.parse.ParseHtml._

object crawler{
  def ParseFile(path:String): Unit = {
    parse(path)
  }

  def main(args: Array[String]): Unit = {
    ParseFile(args{0})
  }
}