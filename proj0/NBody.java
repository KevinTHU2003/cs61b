public class NBody {
    public static double readRadius(String filename){
        In in = new In(filename);
        in.readInt();
        double r = in.readDouble();
        return r;
    }

    public static Planet[] readPlanets(String filename){
        In in = new In(filename);
        int n = in.readInt();
        in.readDouble();
        Planet[] planets = new Planet[n];
        for(int i=0; i<n; i++){
            planets[i] = new Planet(in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble(), in.readString());
        }
        return planets;
    }

    public static void main(String[] args){
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        Planet[] planets = readPlanets(filename);
        int n = planets.length;
        double radius = readRadius(filename);
        StdDraw.setScale(-radius,radius);
        StdDraw.enableDoubleBuffering();
        for(int t=0; t<T; t+=dt){
            double[] xForces = new double[n];
            double[] yForces = new double[n];
            for(int i=0; i<n; i++){
                xForces[i] = planets[i].calcNetForceExertedByX(planets);
                yForces[i] = planets[i].calcNetForceExertedByY(planets);
            }
            StdDraw.clear();
            StdDraw.picture(0, 0, "images/starfield.jpg");
            for(int i=0; i<n; i++){
                planets[i].update(dt, xForces[i], yForces[i]);
                planets[i].draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
        }
        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < planets.length; i++) {
        StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
            planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
            planets[i].yyVel, planets[i].mass, planets[i].imgFileName);   
        }
    }
}
