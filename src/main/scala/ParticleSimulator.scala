object ParticleSimulator {
    def main(args: Array[String]): Unit = {
        val maxIts = 100000
        val maxTime = 5
        //Vect.test

        val p1 = new Particle("testP1", 1, 1.0)
        val p2 = new Particle("testP2", 1, 1e-20, new Vect("", 1, 0, 0), new Vect ("", 0, 1, 0))
        val testSystem = new PSystem("TestSystem1")
        
        testSystem.addParts(p1, p2)

        printf(p1.gravF(p2).toString())

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

            if(time % (2 * 314) == 0) testSystem.printParticles(false, false, true)
            
            lastTime = time
            its += 1
        }
    }
}