package elrio.cfg

import com.fasterxml.jackson.annotation.{JsonCreator, JsonProperty}
import javax.persistence.{Entity, Id}
import scala.beans.BeanProperty

@Entity
case class FeedCfg @JsonCreator() ( @Id @BeanProperty @JsonProperty("name") var name: String,
                                    @BeanProperty @JsonProperty("source") source: SourceCfg,
                                    @BeanProperty @JsonProperty("pakage_validation") packageValidation: PackageValidationCfg,
                                    @BeanProperty @JsonProperty("data_validation") dataValidation: DataValidationCfg,
                                    @BeanProperty @JsonProperty("projections") projections: Array[ProjectionCfg]
                                  )
