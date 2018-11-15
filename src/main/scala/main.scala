import java.io.File

import com.agileengine.parse.ParseHtml._
import com.typesafe.scalalogging.LazyLogging

object crawler extends LazyLogging{

  def ParseFile(path:String,checkPath:String): Unit = {
    val file = new File(path)
    val checkFile = new File(checkPath)
    val original = parse(file);
    val elementXPath = parse(checkFile, original)


    logger.info(elementXPath.tree)
  }

  def main(args: Array[String]): Unit = {
    ParseFile(args{0},args{1})
  }
}