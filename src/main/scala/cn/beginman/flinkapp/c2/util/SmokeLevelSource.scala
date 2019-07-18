/*
 * Copyright 2015 Fabian Hueske / Vasia Kalavri
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.beginman.flinkapp.c2.util

import cn.beginman.flinkapp.c2.util.SmokeLevel.SmokeLevel
import org.apache.flink.streaming.api.functions.source.RichParallelSourceFunction
import org.apache.flink.streaming.api.functions.source.SourceFunction.SourceContext

import scala.util.Random

/**
  * Flink SourceFunction to generate random SmokeLevel events.
  */
class SmokeLevelSource extends RichParallelSourceFunction[SmokeLevel] {

  var running: Boolean = true

  override def run(srcCtx: SourceContext[SmokeLevel]): Unit = {

    val rand = new Random()

    while (running) {

      if (rand.nextGaussian() > 0.8 )
        srcCtx.collect(SmokeLevel.High)
      else
        srcCtx.collect(SmokeLevel.Low)

      Thread.sleep(1000)
    }

  }

  override def cancel(): Unit = {
    running = false
  }

}
