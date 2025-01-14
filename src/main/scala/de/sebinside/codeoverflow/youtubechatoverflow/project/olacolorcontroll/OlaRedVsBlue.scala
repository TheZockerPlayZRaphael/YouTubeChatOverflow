package de.sebinside.codeoverflow.youtubechatoverflow.project.olacolorcontroll

import com.decodified.scalassh.SshClient
import de.sebinside.codeoverflow.youtubechatoverflow.backend.evaluation.ChatEvaluation

/**
  * Created by seb on 12.12.16.
  */
private[olacolorcontroll] class OlaRedVsBlue extends OlaColorControl {

  val UNIVERSE: Int = 1
  val INTERVAL: Int = 1000

  override protected def evaluate(evaluation: ChatEvaluation, client: SshClient): Unit = {

    while (true) {

      // Get words
      val words = evaluation.getWordHistogram(INTERVAL)

      var redValue = 0
      var blueValue = 0
      var greenValue = 0
      var pinkValue = 0

      // Get values
      for ((name, value) <- words) {

        if (name.toUpperCase.contains("RED"))
          redValue = value
        else if (name.toUpperCase.contains("BLUE"))
          blueValue = value
        else if (name.toUpperCase.contains("GREEN"))
          greenValue = value
        else if (name.toUpperCase.contains("PINK"))
          pinkValue = value

      }

      val myList = List(redValue, blueValue, greenValue, pinkValue)

      println("RED: %d, BLUE: %d, GREEN: %d, PINK: %d".format(redValue, blueValue, greenValue, pinkValue))

      // Change colors
      if (myList.max == redValue)
        setColor(client, UNIVERSE, (255, 255, 0, 0))
      else if (myList.max == blueValue)
        setColor(client, UNIVERSE, (255, 0, 0, 255))
      else if (myList.max == greenValue)
        setColor(client, UNIVERSE, (255, 0, 255, 0))
      else
        setColor(client, UNIVERSE, (255, 255, 0, 186))

      // Wait
      Thread.sleep(INTERVAL)
    }
  }

  override private[project] def getName = "RedVsBlue"

  override private[project] def getDescription = "Changes the light color using the open lighting architecture to red or blue according to the chat."
}

object OlaRedVsBlue {
  def apply(): OlaRedVsBlue = new OlaRedVsBlue()
}