package elrio.cfg

import com.fasterxml.jackson.annotation.{JsonCreator, JsonProperty}
import javax.persistence.{Entity, Id}
import scala.beans.BeanProperty

@Entity
case class FeedCfg @JsonCreator() ( @Id @BeanProperty @JsonProperty("name") var name: String,
                                    @BeanProperty @JsonProperty("source") source: SourceCfg,
                                    @BeanProperty @JsonProperty("validation") validation: PackageValidationCfg,
                                    @BeanProperty @JsonProperty("projections") projections: Array[ProjectionCfg]
                                  )
