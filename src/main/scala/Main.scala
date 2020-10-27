import scala.xml._

import scala.collection.mutable


object Main {
    def main(args: Array[String]): Unit = {
        val maxIts = 1000
        val maxTime = 10 //MAX TIMER IN SECONDS

        val particlePool: mutable.ListBuffer[Particle] = mutable.ListBuffer[Particle]()

        val systemPool: mutable.ListBuffer[PSystem] = mutable.ListBuffer[PSystem]()

        //----------------------- LODAING INFO STREAMS FOR OBJECTS AND DATA --------------------------------
        val particlesXML = XML.load("particles.xml")
        
        for(n <- particlesXML \ "particle") particlePool += Particle(n)
        //-------------------------------------------------------------------------------------------------
            
        val testSystem = new PGravSystem("TestSystem1")
            
            
        systemPool += testSystem
        testSystem.addParts(particlePool)
            
        testSystem.run(0, 5, false, true)

        testSystem.documentParticleData("save"+System.nanoTime()+".xml")
    }
}