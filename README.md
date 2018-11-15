# AgileEngine backend-XML Scala snippets

It is built on top of [Jsoup](https://jsoup.org/).

You can use Jsoup for your solution or apply any other convenient library. 
=======
# crawler
crawler

Note: need to use SBT to run the solution. didnt have time to remove the multiple main access while packeting.

command to run ~run originalPath newFilePath

output will be the XPath to the element.
e.g html[1] > body > div[1] > div[2] > div > div > div[1] > div > div > a
and be displayed in the logger.Info (added a dependency for the logger.)
