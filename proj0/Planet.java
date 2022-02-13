public class Planet{
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
    private static final double G = 6.67e-11;   

    public Planet(double xP, double yP, double xV, double yV, double m, String img){
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Planet(Planet p){
        xxPos = p.xxPos;
        yyPos = p.yyPos;
        xxVel = p.xxVel;
        yyVel = p.yyVel;
        mass = p.mass;
        imgFileName = p.imgFileName;
    }

    public double calcDistance(Planet p){
        double dx = p.xxPos - xxPos;
        double dy = p.yyPos - yyPos;
        return Math.sqrt(dx*dx + dy*dy);
    }

    public double calcForceExertedBy(Planet p){
        return G*mass*p.mass/(calcDistance(p)*calcDistance(p));
    }

    public double calcForceExertedByX(Planet p){
        double dx = p.xxPos - xxPos;
        return calcForceExertedBy(p)*dx/calcDistance(p);
    }

    public double calcForceExertedByY(Planet p){
        double dy = p.yyPos - yyPos;
        return calcForceExertedBy(p)*dy/calcDistance(p);
    }

    public double calcNetForceExertedByX(Planet[] planets){
        double ans = 0;
        for(Planet p: planets){
            if(this.equals(p) == true){
                continue;
            }
            ans += calcForceExertedByX(p);
        }
        return ans;
    }

    public double calcNetForceExertedByY(Planet[] planets){
        double ans = 0;
        for(Planet p: planets){
            if(this.equals(p) == true){
                continue;
            }
            ans += calcForceExertedByY(p);
        }
        return ans;
    }

    public void update(double dt, double Fx, double Fy){
        double ax = Fx / mass;
        double ay = Fy / mass;
        xxVel += ax * dt;
        yyVel += ay * dt;
        xxPos += xxVel * dt;
        yyPos += yyVel * dt;
    }

    public void draw(){
        StdDraw.picture(xxPos, yyPos, imgFileName);
    }
}