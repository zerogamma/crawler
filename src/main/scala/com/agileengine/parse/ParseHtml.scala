package parse

import java.io.File

import com.agileengine.xml.JsoupFindByIdSnippet

object ParseHtml {

  def parse(path: String): String = {
    val test = JsoupFindByIdSnippet.findElementById(new File(path),"make-everything-ok-button")
    println(test)
    test.toString
  }
}
