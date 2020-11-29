import scalafx.scene.paint.Color
class Ray (val origin: Vect, val dir /*unit vector!*/: Vect) {
    

    def intersect(p: Particle): (Double, Color) = {
            val pos = p.pos

            val dr = dir - origin
            
            val a = dr.dot(dr)
         
            val b = 2 * (dr.dot(origin - pos))

            val c = pos.dot(pos) + origin.dot(origin) + ((-2) * (pos.dot(origin))) - (p.radius * p.radius)

            val disc = (b*b) - 4*a*c

            return if(disc > 0) ((-b - scala.math.sqrt(disc))/(2*a), p.color) else (0.0, Color.Black)
    }

    

    object Ray {
        
        def apply(origin: Vect, dir: Vect) = {
            new Ray(origin, dir)
        }

        
    }
}