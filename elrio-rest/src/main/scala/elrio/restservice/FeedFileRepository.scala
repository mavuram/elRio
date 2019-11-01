package elrio.restservice

import java.lang

import elrio.CfgLoder
import elrio.cfg.FeedCfg
import org.springframework.data.repository
import org.springframework.stereotype.Repository

import scala.collection.JavaConverters._

@Repository
class FeedFileRepository extends repository.Repository[FeedCfg, String]{
    def save(feedCfg: FeedCfg) : Unit = {
    }

  def findByIdo(feedId: String): Option[FeedCfg] = {
    CfgLoder.load(feedId)
  }

//  override def findById(id: String): Optional[FeedCfg] = {
//    Optional.ofNullable(CfgLoder.load(id).orNull)
//  }
//
  def findAll: lang.Iterable[FeedCfg] = {
    CfgLoder.load().asJava
  }
//
//  override def save[S <: FeedCfg](s: S): S = {
//    s //TODO: implement this
//  }
//
//  override def saveAll[S <: FeedCfg](cfgs: lang.Iterable[S]): lang.Iterable[S] = {
//    cfgs //implement this
//  }
//
//  override def existsById(id: String): Boolean = {
//    CfgLoder.load(id) match {
//      case Some(a) => true
//      case _ => false
//    }
//  }
//
//  override def findAllById(ids: lang.Iterable[String]): lang.Iterable[FeedCfg] = {
//    ids.asScala.map(id => CfgLoder.load(id)).flatten.asJava
//  }
//
//  override def count(): Long = {
//    5
//    //TODO: implement this
//  }
//
//  override def deleteById(id: String): Unit = {
//    //TODO: implement this
//  }
//
//  override def delete(t: FeedCfg): Unit = {
//    //TODO: implement this
//  }
//
//  override def deleteAll(iterable: lang.Iterable[_ <: FeedCfg]): Unit = {
//    //TODO: implement this
//  }
//
//  override def deleteAll(): Unit = {
//    //TODO: implement this
//  }
}

//object FeedFileRepository {
//  def main(args: Array[String]): Unit = {
//    val ff = new FeedFileRepository()
//    ff.findById("foo")
//  }
//}
//class FeedFileRepository extends CrudRepository[FeedCfg, String]{
////  def save(feedCfg: FeedCfg) = {
////  }
//
//  def findByIdo(feedId: String): Option[FeedCfg] = {
//    CfgLoder.load(feedId)
//  }
//
//  override def findById(id: String): Optional[FeedCfg] = {
//    Optional.ofNullable(CfgLoder.load(id).orNull)
//  }
//
//  def findAll: lang.Iterable[FeedCfg] = {
//    CfgLoder.load().toIterable.asJava
//  }
//
//  override def save[S <: FeedCfg](s: S): S = {
//    s //TODO: implement this
//  }
//
//  override def saveAll[S <: FeedCfg](cfgs: lang.Iterable[S]): lang.Iterable[S] = {
//    cfgs //implement this
//  }
//
//  override def existsById(id: String): Boolean = {
//    CfgLoder.load(id) match {
//      case Some(a) => true
//      case _ => false
//    }
//  }
//
//  override def findAllById(ids: lang.Iterable[String]): lang.Iterable[FeedCfg] = {
//    ids.asScala.map(id => CfgLoder.load(id)).flatten.asJava
//  }
//
//  override def count(): Long = {
//    5
//    //TODO: implement this
//  }
//
//  override def deleteById(id: String): Unit = {
//    //TODO: implement this
//  }
//
//  override def delete(t: FeedCfg): Unit = {
//    //TODO: implement this
//  }
//
//  override def deleteAll(iterable: lang.Iterable[_ <: FeedCfg]): Unit = {
//    //TODO: implement this
//  }
//
//  override def deleteAll(): Unit = {
//    //TODO: implement this
//  }
//}
//
//object FeedFileRepository {
//  def main(args: Array[String]): Unit = {
//    val ff = new FeedFileRepository()
//    ff.findById("foo")
//  }
//}
