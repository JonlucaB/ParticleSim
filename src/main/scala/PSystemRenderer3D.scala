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
import scalafx.animation.AnimationTimer
import scalafx.scene.canvas.Canvas


object PSystemRenderer3D extends JFXApp {app =>
    //System.setProperty("prism.dirtyopts", "false")
    
    private final val root = new Group()
    private final val axisGroup = new Group()
    private final val world = new Xform()
    private final val camera: PerspectiveCamera = new PerspectiveCamera(true)
    private final val cameraXform = new Xform()
    private final val cameraXform2 = new Xform()
    private final val cameraXform3 = new Xform()
    private final val cameraDistance: Double = 150
    private final var particleGroup = new Xform()
    private var mousePosX: Double = .0
    private var mousePosY: Double = .0
    private var mouseOldX: Double = .0
    private var mouseOldY: Double = .0
    private var mouseDeltaX: Double = .0
    private var mouseDeltaY: Double = .0

    // Use for later when trying to show buttons and timer ===============
    val canvas = new Canvas(1024, 768)
    val gc = canvas.graphicsContext2D
    gc.fill = Color.BLACK
    //====================================================================

    val maxTime = 60 
    val maxIts = 100

    val particlePool = loadParticlesFromFile("particlesTestOne.xml")
    val systemPool = mutable.ListBuffer[PSystem]() //need to add 


    val rSystem = new PGravSystem("TestSystem1")
    rSystem.addParts(particlePool)

    // buildScene()
    buildCamera()
    // buildAxes()
    // updateSystem()

    particleGroup.children ++= particlePool.map(p => p.pSphere)

    root.children += particleGroup

    //root.getChildren().add(canvas)

    stage = new JFXApp.PrimaryStage {
        scene = new Scene(root, 1024, 768, depthBuffer = true, antiAliasing = SceneAntialiasing.Balanced) {
            fill = Color.Gray
            title = "Particle Simulator for System: "+rSystem.name
            camera = app.camera


            var lastTime = 0L
            val simTimer= AnimationTimer(t => {
                if(lastTime > 0)
                {
                    rSystem.update((t - lastTime)/1e9)
                }
                lastTime = t
                //gc.fillText(t.toString, 100, 100)
            })

            simTimer.start()
        }
        handleMouse(scene(), root)
    }

    def updateSystem(dT: Double): Unit = rSystem.update(dT)


    // Author of this camera code is @author Jarek Sacha
    def buildCamera(): Unit = {
        root.children += cameraXform
        cameraXform.children += cameraXform2
        cameraXform2.children += cameraXform3
        cameraXform3.children += camera
        cameraXform3.rotateZ = 180.0
        camera.nearClip = 0.1
        camera.farClip = 10000.0
        camera.translateZ = -cameraDistance
        cameraXform.ry.angle = 320.0
        cameraXform.rx.angle = 40
    }

    private def handleMouse(scene: Scene, root: Node): Unit = {
        scene.onMousePressed = (me: MouseEvent) => {
            mousePosX = me.sceneX
            mousePosY = me.sceneY
            mouseOldX = me.sceneX
            mouseOldY = me.sceneY
        }
        scene.onMouseDragged = (me: MouseEvent) => {
            mouseOldX = mousePosX
            mouseOldY = mousePosY
            mousePosX = me.sceneX
            mousePosY = me.sceneY
            mouseDeltaX = mousePosX - mouseOldX
            mouseDeltaY = mousePosY - mouseOldY
            val modifier = if (me.isControlDown) 0.1 else if (me.isShiftDown) 10 else 1.0
            val modifierFactor = 0.1
            if (me.isPrimaryButtonDown) {
                cameraXform.ry.angle = cameraXform.ry.angle() - mouseDeltaX * modifierFactor * modifier * 2.0
                cameraXform.rx.angle = cameraXform.rx.angle() + mouseDeltaY * modifierFactor * modifier * 2.0
            } else if (me.isSecondaryButtonDown) {
                val z = camera.translateZ()
                val newZ = z + mouseDeltaX * modifierFactor * modifier
                camera.translateZ = newZ
            } else if (me.isMiddleButtonDown) {
                cameraXform2.t.x = cameraXform2.t.x() + mouseDeltaX * modifierFactor * modifier * 0.3
                cameraXform2.t.x = cameraXform2.t.y() + mouseDeltaY * modifierFactor * modifier * 0.3
            }
        }
    }
}