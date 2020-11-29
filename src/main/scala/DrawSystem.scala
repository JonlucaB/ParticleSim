// class DrawSystem(maxIts: Long, 
//                 maxTime: Long, 
//                 useIts: Boolean, 
//                 useTime: Boolean) extends JFXApp 
// {
//     def run = {
//         timer.start
//     }

//     stage = new JFXApp.PrimaryStage {
//             title = name
//             scene = new Scene(1000, 1000) {
//                 val border = new BorderPane
//                 val canvas  = new Canvas(1000, 1000)
//                 val gc = canvas.graphicsContext2D
//                 border.center = canvas
//                 root = border
                
//                 canvas.height <== border.height
//                 canvas.width <== border.width
                
//                 var time = System.nanoTime()
//                 val startTime = time
//                 var lastTime = -1L
//                 var its = 0
                
//                 val timer = AnimationTimer(time => {
//                     if(lastTime == 0) lastTime = time
//                     else if (check(its) && !paused) {
//                         val dT = (time - lastTime)/1e9d
//                         lastTime = time
                        
//                         drawnParticles = particles.map(p => p.toDrawParticle)
//                         gc.fill = Color.Black
//                         gc.fillText((time/1e9).toString, 0, 0)
//                         gc.fill = Color.White
//                         gc.fillRect(0, 0, canvas.width.value, canvas.height.value)
                        
//                         for (drawParticle(r, c, p) <- drawnParticles) {
//                             val  (x, y, z) = p.getData
//                             gc.fill = c
//                             gc.fillOval(x * 100, y * 100, r, r)
//                         }
//                     }
//                     else if (paused)
//                     {
//                         gc.fill = Color.Red
//                         gc.fillText("Simulation Paused", canvas.width.value/3, canvas.width.value/2)
//                     }
//                     else {
//                         gc.fill = Color.Red
//                         gc.fillText("Simulation Complete", 0, 0)
//                     }
//                 })

//                 def check(iterations: Long): Boolean = {
//                     var timePass = !useTime
                    
//                     if(useTime) timePass = (time - startTime - pausedTimePassed)/1e9 < maxTime
                    
//                     var itsPass = !useIts 
                    
//                     if(useIts) itsPass = iterations < maxIts
                    
//                     timePass && itsPass
//                 }
//             }
//         }
//         runTerminated = true
// }