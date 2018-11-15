package com.agileengine.parse

import java.io.File
import java.text.AttributedCharacterIterator.Attribute
import java.util
import javafx.geometry.Pos

import com.agileengine.xml.{JsoupCssSelectSnippet, JsoupFindByIdSnippet}
import org.jsoup.nodes
import org.jsoup.nodes.{Attributes, Element}
import org.jsoup.select.Elements

import scala.util.Try

object ParseHtml{

  implicit class NotNullOption[T](val t: Try[T]) extends AnyVal{
    def toNotNullOption = t.toOption.flatMap{Option(_)}
  }

  def findElementIndexInParent(element: Element) : String = {
    element.elementSiblingIndex() match {
      case 0 => ""
      case index:Int => "["+index.toString+"]"
    }
  }

  def findTree(element: Element, resp: String): String ={
    val ParentsElement = element.parent()
    ParentsElement.hasParent match {
      case true =>
        findTree(ParentsElement,ParentsElement.tag().toString + findElementIndexInParent(element) + " > " + resp)
      case false =>
        resp
    }
  }

  def getTree(element:Try[Element]): originalData ={
    element.toNotNullOption match {
      case Some(b) => originalData(b,findTree(b,b.tag().toString))
      case None => originalData( null, "")
    }
  }

  def getElementBySiblins(elements: Elements,  att: util.List[nodes.Attribute], pos: Int ,acumulative:String) : originalData ={
    val cssQuery = "["+att.get(pos)+"]"
    val findElement = elements.select(cssQuery)
      findElement.size() match {
        case 1 => {
          originalData(findElement.first(), findTree(findElement.first(), findElement.first().tag().toString))
        }
        case _ => getElementBySiblins(elements, att, pos+1,acumulative)
      }
  }

  def getElementByClass(file: File,  element: originalData , att: util.List[nodes.Attribute], pos: Int ) : originalData = {
    val cssQuery = element.element.tag()+"["+att.get(pos)+"]"
    val elem = JsoupCssSelectSnippet.findElementsByQuery(file,cssQuery)
    val treelog = elem.get.toArray.size match {
      case 1 =>  {
        originalData(elem.get.first(),findTree(elem.get.first(),elem.get.first().tag().toString))
      }
      case 0 => {
        getElementByClass(file,element, att, pos + 1)
      }
      case x if x > 1 =>{
        getElementBySiblins(elem.get, att,pos+1,cssQuery)
      }
    }
    treelog
  }

  def findByOtherMethod(file: File, element: originalData) : originalData = {
    val att = element.element.attributes().asList()
    getElementByClass(file,element , att, 0)
  }

  def parse(file: File): originalData = {
    val resp = JsoupFindByIdSnippet.findElementById(file,"make-everything-ok-button")
    getTree(resp)
  }

  def parse(file: File, element: originalData) : originalData = {
    val resp = JsoupFindByIdSnippet.findElementById(file,"make-everything-ok-button")
    val treeLog = resp.toNotNullOption match {
      case Some(a) => originalData(a,findTree(a,a.tag().toString))
      case None => findByOtherMethod(file, element)
    }
    treeLog
  }

  case class originalData (element: Element, tree:String)
}
