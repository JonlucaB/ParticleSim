//This class should not do ANY physcics calculations. All of the particles that are being rendered should be made prior to creating the scene, along with a 
//choice to show axis or not (X, Y, or Z). The renderer should also take in key inputs that do the following:
//s -> start the simulation, aka, start the animation timer within the stage
//p -> pause the simulation, aka, pause the animation timer
//r -> restart the simulation, aka, call restart on the PSystem that the renderer is rendering
//mouse inputs -> should be able to rotate the camera allowing for different viewing angles
//arrow key inputs (maybe) -> move the camera around on 3 dimensions, would require many weird calculations since camera can be rotated
package ParticleSim

import scalafx.Includes._
import scalafx.animation.Timeline
import scalafx.application.JFXApp
import scalafx.scene._
import scalafx.scene.input.{KeyCode, KeyEvent, MouseEvent}
import scalafx.scene.paint.{Color, PhongMaterial}
import scalafx.scene.shape.{Box, Cylinder, Sphere}
import scalafx.scene.transform.Rotate
import scala.collection.mutable
import PUtil._

object PSystemRenderer3D extends JFXApp {app =>
    System.setProperty("prism.dirtyopts", "false")

    private final val root = new Group()
    private final val axisGroup = new Group()
    private final val world = new Xform()
    private final val camera: PerspectiveCamera = new PerspectiveCamera(true)
    private final val cameraXform = new Xform()
    private final val cameraXform2 = new Xform()
    private final val cameraXform3 = new Xform()
    private final val cameraDistance: Double = 450
    private final val particleGroup = new Xform()

    val particlePool = loadParticlesFromFile("particles.xml")
    val systemPool = mutable.ListBuffer[PSystem]() //need to add 
    val rSystem = new PGravSystem("TestSystem1", Vect("View", 0, 0, 0), Vect("Forward", 0, 0, 1), Vect("Up", 0, 1, 0))

    // buildScene()
    // buildCamera()
    // buildAxes()
    // buildParticles()

    stage = new JFXApp.PrimaryStage {
        scene = new Scene(root, 1024, 768, depthBuffer = true, antiAliasing = SceneAntialiasing.Balanced) {
            fill = Color.Gray
            title = "Particle Simulator for System: "+rSystem.name
        }
    }


}