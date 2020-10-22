import scala.xml._

import scala.collection.mutable


object Main {
    def main(args: Array[String]): Unit = {
        val maxIts = 1000
        val maxTime = 10 //MAX TIMER IN SECONDS

        val particlePool: mutable.ListBuffer[Particle] = mutable.ListBuffer[Particle]()

        val systemPool: mutable.ListBuffer[System] = mutable.ListBuffer[System]()

        //----------------------- LODAING INFO STREAMS FOR OBJECTS AND DATA --------------------------------
        val particlesXML = XML.load("particles.xml")
        
        for(n <- particlesXML \ "particle") particlePool += Particle(n)
        //-------------------------------------------------------------------------------------------------
            
            val testSystem = new PGravSystem("TestSystem1")
            
            
            systemPool += testSystem
            
            println("Length of particlePool ->"+particlePool.size)
            println("Length of systemPool ---> "+systemPool.size)
            
            testSystem.addParts(particlePool)
            
            var time = System.nanoTime()
            val startTime = time
            var lastTime = -1L
            var its = 0
            
            while((time - startTime)/1e9 < maxTime)
            {
                time = System.nanoTime()
                if(lastTime > 0)
                {
                    val dT = (time - lastTime)/1e9d
                    testSystem.update(dT)
                    
                }
                
                if(time/1e9 % 6 == 0) testSystem.printParticles(false, false, true)
                
                lastTime = time
                its += 1
            }        
            // val writeXMLParticles = <Particles>{particlePool.map(p => p.toXML)}</Particles>
            // scala.xml.XML.save("particles.xml", writeXMLParticles)
        }
    }