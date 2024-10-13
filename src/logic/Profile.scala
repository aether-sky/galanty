package sky.aether
package logic

import in.dogue.antiqua.Antiqua.*

import java.time.LocalDateTime

object Profile {
  def load:Profile = {
    val profileName = "Default"
    Profile(profileName, LocalDateTime.now(), 1, CharRecord.load(profileName))
  }
}

case class Profile(name:String, created:LocalDateTime, characterTokens:Int, characters:Seq[CharRecord])

