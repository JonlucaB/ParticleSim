object ParticleSimulator {
    def main(args: Array[String]): Unit = {
        val maxIts = 100
        //Vect.test

        val p1 = new Particle("testP1", 1, 1.0)
        val p2 = new Particle("testP2", 1, 1e-20, new Vect("", 0, 0, 0), new Vect ("", 0, 1, 0))
        val testSystem = new PSystem("TestSystem1")
        
        testSystem.addParts(p1, p2)

        var time = System.nanoTime()
        var lastTime = time
        var dT = 0L

        var its = 0

        while(its <= maxIts)
        {
            time = System.nanoTime()

            dT = (time - lastTime)/1e9
            testSystem.update(dT)

            if(its % 10 == 0) testSystem.printParticles(false, false, true)

            lastTime = time
            its += 1
        }
    }
}