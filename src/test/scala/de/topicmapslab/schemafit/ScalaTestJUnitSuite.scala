package de.topicmapslab.schemafit

import de.topicmapslab.tmql4j.components.processor.runtime.TMQLRuntimeFactory
import org.tmapi.core.TopicMap
import org.tmapi.core.TopicMapSystem
import org.tmapi.core.TopicMapSystemFactory
import scala.collection.mutable.ListBuffer

import junit.framework.TestCase
import org.junit._
import Assert._

class ScalaTestJUnitSuite extends TestCase {

  val tmSys = TopicMapSystemFactory.newInstance().newTopicMapSystem()
  val _tm = tmSys.createTopicMap("http://www.example.org/map1")
  val tmql = TMQLRuntimeFactory.newFactory().newRuntime()
  val _sf = new SchemaFit(_tm, tmql)

  @Test
  def testSometddhing() { // Uses ScalaTest assertions

    val hans = _tm.createTopicByItemIdentifier(_tm.createLocator("http://foo.net/hans"));
    hans.createName("hansi");

    
    println( _tm.getTopics().iterator().next() );
    println(_sf.execute("\"http://foo.net/hans\"   "));

  }

}