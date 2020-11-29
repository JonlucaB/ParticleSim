/*
Vector Class

The purpose of this class is to provide a model for a 3D vector with mathematical functionalities.
It consists of Double, and has Polymorphic functions. The particle system relies heavily on 
the functionality of the 3D Vector class
*/
import scala.xml._

class Vect (val name : String = "", private var x: Double = 0.0, private var y: Double = 0.0, private var z: Double = 0.0) extends Serializable
{
    
    //Provides the three dimensional data for a Vector
    def getData: (Double, Double, Double) = (x, y, z)
    
    //vect1 + vect2 
    // F = (m1 * m2 / |d|^3) * d

    //Add a Vector to a Vector
    //DIAGNOSTIC_LEVEL 1
    def + (data: Vect): Vect = {
        val (opX, opY, opZ) = data.getData

        val newX = opX + x
        val newY = opY + y
        val newZ = opZ + z

        return Vect("("+name+" "+data.name+")", newX, newY, newZ)
    }

    //Difference of two Vectors
    //DIAGNOSTIC_LEVEL 2
    def - (data: Vect): Vect = {
        val (opX, opY, opZ) = data.getData

        val newX = x - opX
        val newY = y - opY
        val newZ = z - opZ
        return Vect("("+name+" "+data.name+")", newX, newY, newZ)
    }

    //Two instances: Either we are given a scalar or a vector with the '*' symbol
    //In the case we get a scalar, we simply multiply the magnitude of the vector by that scalar
    //In the case we get a vector, we dot the vector
    //DIAGNOSTIC_LEVEL 3
    def * (data: Double): Vect = {
        val newX = x * data
        val newY = y * data
        val newZ = z * data
        Vect("("+data+" * "+name+")", newX, newY, newZ)
    }

    def * (data: Vect) = {
        val (opX, opY, opZ) = data.getData
        val dotX = x * opX
        val dotY = y * opY
        val dotZ = z * opZ
        Vect("("+data.name+" * "+name+")", dotX, dotY, dotZ)
    }

    //Cross product of a vector
    //DIAGNOSTIC_LEVEL 4
    def x (data: Vect): Vect = {
        val (opX, opY, opZ) = data.getData

        val newX = (y * opZ) - (z * opY)
        val newY = (z * opX) - (x * opZ)
        val newZ = (x * opY) - (y * opX)
        
        Vect("("+name+" X "+data.name+")", newX, newY, newZ)
    }
    
    //Division with a scalar
    def / (data: Double): Vect = {
        val newX = x / data
        val newY = y / data
        val newZ = z / data

        Vect(name+" / "+data, newX, newY, newZ)
    }
    
    
    //Making a Vecotr equal to another
    //DIAGNOSTIC_LEVEL 4
    def equal (data: Vect): Vect = {
        val (eqX, eqY, eqZ) = data.getData
        
        val newX = eqX
        val newY = eqY
        val newZ = eqZ

        Vect("("+name+")", newX, newY, newZ)
    }
    
    //The magnitude of a Vector
    def mag: Double = {
        val magX = x*x
        val magY = y*y
        val magZ = z*z
        
        Math.sqrt(magX + magY + magZ)
    }

    def dot (data: Vect): Double = {
        val (dX, dY, dZ) = data.getData
        val dotX = dX * x
        val dotY = dY * y
        val dotZ = dZ * z

        dotX + dotY + dotZ
    }

    def unit: Vect = {
        val norm = mag
        val normX = x / norm
        val normY = y / norm
        val normZ = z / norm

        Vect("Unit ("+name+")", normX, normY, normZ)
    }

    override def toString(): String = "("+x+", "+y+", "+z+")\n"

    def toXML: scala.xml.Node = <vect nm={name} xComp={x.toString} yComp={y.toString} zComp={z.toString}/>
    
}

object Vect {
    def apply(name: String, x: Double, y: Double, z: Double) = new Vect("", x, y, z)

    def apply(node: scala.xml.Node): Vect = {
        val nm = (node \ "@nm").text
        new Vect(nm, (node \ "@xComp").text.toDouble, (node \ "@yComp").text.toDouble, (node \ "@zComp").text.toDouble)
    }

    val DIAGNOSTIC_LEVEL = 6

    def test = {
        
        //Testing addition
        if(DIAGNOSTIC_LEVEL <= 1) 
        {
            println("///////////////////////////////////////////////////////////\n")
            println("//           TESTING ADDITION WITH VECTORS               //\n")
            println("///////////////////////////////////////////////////////////\n")

            val testVect = new Vect("One")

            val testVect2 = new Vect("Two", 1.0, 1.0, 1.0)

            val (x, y, z) = testVect.getData

            val addVect = testVect + testVect2

            println("testVect = "+x+" "+y+" "+z+"\n")

            println("Should be => \nOne Two = (1.0, 1.0, 1.0)\n"+addVect.toString)

            val testVect3 = new Vect("Three", 2.0, 2.0, 2.0)

            val testVect4 = new Vect("Four", -1.0, -1.0, -1.0)

            val testVect5 = new Vect("Five", 3.0, 4.0, 5.0)

            val addVect2 = (testVect3 + testVect4) + testVect5

            println("Should be => \nThree Four Five = (4.0, 5.0, 6.0)\n"+addVect2.toString)
        }

        //testing subtraction
        if(DIAGNOSTIC_LEVEL <= 2)
        {

            println("///////////////////////////////////////////////////////////\n")
            println("//           TESTING SUBTRACTION WITH VECTORS            //\n")
            println("///////////////////////////////////////////////////////////\n")

            val testVect = new Vect("One")

            val testVect2 = new Vect("Two", 1.0, 1.0, 1.0)

            val (x, y, z) = testVect.getData

            val subVect = testVect - testVect2

            println("testVect = "+x+" "+y+" "+z+"\n")

            println("Should be => \nOne Two = (1.0, 1.0, 1.0)\n\n"+subVect.toString)

            Util.HitReturnToContinue

            val testVect3 = new Vect("Three", 2.0, 2.0, 2.0)

            val testVect4 = new Vect("Four", -1.1, -1.2, -1.3)

            val testVect5 = new Vect("Five", 3.0, 4.0, 5.0)

            val addVect2 = (testVect3 - testVect4) - testVect5

            println("Should be => \nThree Four Five = (0.1, -0.8, -1.7)\n\n"+addVect2.toString)

            Util.HitReturnToContinue
        }

        if(DIAGNOSTIC_LEVEL <= 3)
        {   
            println("///////////////////////////////////////////////////////////\n")
            println("//           TESTING DOT WITH VECTORS                    //\n")
            println("///////////////////////////////////////////////////////////\n")

            val testVect = new Vect("One", 3.0, 2.5, 2.0)

            val testVect2 = new Vect("Two", 2.0, 2.5, 3.0)

            val (x, y, z) = testVect.getData

            val vectMultVect = testVect * testVect2

            println("testVect = "+x+" "+y+" "+z+"\n")

            println("Should be => \nOne Two = (6.0, 6.25, 6.0)\n\n"+vectMultVect.toString)

            Util.HitReturnToContinue

            println("///////////////////////////////////////////////////////////\n")
            println("//           TESTING MULTIPLY WITH SCALARS               //\n")
            println("///////////////////////////////////////////////////////////\n")

            val testScalVect = new Vect("One", 3.0, 2.5, 2.0)

            val (xs, ys, zs) = testVect.getData

            val scalMultVect = testScalVect * 4.0

            println("testVect = "+xs+" "+ys+" "+zs+"\n")

            println("Should be => \nOne Two = (12.0, 10.0, 8.0)\n\n"+scalMultVect.toString)

            Util.HitReturnToContinue
        }
        
        if(DIAGNOSTIC_LEVEL <= 4)
        {
            println("///////////////////////////////////////////////////////////\n")
            println("//           TESTING CROSS PRODUCT                       //\n")
            println("///////////////////////////////////////////////////////////\n")

            val testVect = new Vect("One", 3.0, 2.5, 2.0)

            val testVectTwo = new Vect("Two", 2.6, 3.8, 10.11)

            val scalMultVect = testVect x testVectTwo

            println("Should be => \nOne X (One * 4.0) = (17.675, -25.13, 4.9)\n\n"+scalMultVect.toString)

            Util.HitReturnToContinue
        }

        if(DIAGNOSTIC_LEVEL <= 5)
        {
            println("///////////////////////////////////////////////////////////\n")
            println("//                    TESTING EQUALS                     //\n")
            println("///////////////////////////////////////////////////////////\n")

            val testVect = new Vect("One", 3.0, 2.5, 2.0)

            val testVectTwo = new Vect("Two", 2.6, 3.8, 10.11)

            val scalEqVect = testVect equal testVectTwo

            println("Should be => \n(Two) = (2.6, 3.8, 10.11)\n\n"+scalEqVect.toString)

            Util.HitReturnToContinue
        }

        if(DIAGNOSTIC_LEVEL <= 6)
        {
            println("///////////////////////////////////////////////////////////\n")
            println("//            TESTING MAGNITUDE OF A VECTOR              //\n")
            println("///////////////////////////////////////////////////////////\n")

            val testVect = new Vect("One", 3.0, 2.5, 2.0)

            val testVectTwo = new Vect("Two", 2.6, 3.8, 10.11)

            val oneMag = testVect.mag

            val twoMag = testVectTwo.mag

            println("Should be => \n4.387482193696061\n\n"+oneMag)

            println("Should be => \n11.109099873527107\n\n"+twoMag)            

            Util.HitReturnToContinue
        }
    }
}