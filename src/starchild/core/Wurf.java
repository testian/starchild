package starchild.core;

//  -------------   JOGL-Demo  -------------------------

import javax.media.opengl.*;
import com.sun.opengl.util.*;
import java.awt.*;
import java.awt.event.*;
import starchild.coordinate.*;
import starchild.physics.examples.*;
//import java.util.*;

public class Wurf
             implements WindowListener, GLEventListener, ActionListener, MouseListener, MouseMotionListener, MouseWheelListener
{

     //  ---------  Globale Daten  ---------------

    SunSystem sun;
    double xmin=-6, xmax=6 ;                                 // x-Koordinatenbereich
     double ymin, ymax;                                       // y-Bereich, Initialisierung unten
     double s = 3;                                            // Dreieck-Kante
     Frame f;
     GLCanvas canvas;
     Animator anim;
     Button button = new Button("On / Off");
     double helligkeit = 1;                                   // momentane Helligkeit
     long frameTime = 0;
     boolean lightOn = true;
     int width;
     int height;
     double direction;
     double vektorBetrag;
     double originX;
     double originY;
     long initTime;
     double mouseX;
     double mouseY;
     double time;
     double timescale;
     private Universe universe;
     private double ptime;


     //  ---------  Methoden  --------------------

     void zeichneAchsen(GL gl)                                // Koordinatenachsen zeichnen
     {  gl.glBegin(GL.GL_LINES);
          gl.glVertex2d(xmin,0); gl.glVertex2d(xmax,0);
          gl.glVertex2d(0,ymin); gl.glVertex2d(0,ymax);
        gl.glEnd();
     }

     public void zeichneBall(GL gl, double x, double y) {

                 int n = 20;
                 double c = 3/20.0;
        double alpha = (2*Math.PI)/n;
         gl.glBegin(GL.GL_POLYGON);
             for (int i = 0; i<n;i++) {
            //double sec = Math.sin(System.currentTimeMillis()/1000.0);
            gl.glVertex2d(x+c*Math.cos(i*alpha),y+c*Math.sin(i*alpha));
        }
     gl.glEnd();
     }
     /*void zeichneRotation(GL gl, double s, double position, boolean asPendel)                     // gleichseitiges Dreieck
     {
        
        double c = s/20; //Um Ursprung rotierende Kugel hat einen 10-fach kleineren Radius
        
        
        
        double originX = s*Math.cos(position);
        double originY = s*Math.sin(position);

        int n = 100;
        double alpha = (2*Math.PI)/n;
        gl.glBegin(GL.GL_POLYGON);
        for (int i = 0; i<n;i++) {
            //double sec = Math.sin(System.currentTimeMillis()/1000.0);
            gl.glVertex2d(asPendel ? c*Math.cos(i*alpha) : originX+c*Math.cos(i*alpha),originY+c*Math.sin(i*alpha));
        }
        gl.glEnd();
     }*/


     Wurf()                                               // Konstruktor
     {  f = new Frame("Circle");
        f.setLayout(null);                                    // LayoutManager ausschalten
        width = 800;
        height = 600;
        f.setSize(width, height);
        f.setBackground(Color.gray);
        f.addWindowListener(this);
        canvas = new GLCanvas();
        canvas.addMouseListener(this);
        canvas.addMouseMotionListener(this);
        canvas.addMouseWheelListener(this);
        canvas.setBounds(40, 50, 720, 500);
        canvas.addGLEventListener(this);
        button.setBounds(40, 560, 64, 25);
        button.addActionListener(this);
        f.add(canvas);
        f.add(button);
        f.setVisible(true);
        anim = new Animator(canvas);                          // Animations-Thread 
        direction = Math.PI/4;
        vektorBetrag = 50;
        originX = 0;
        originY = 0;

        //timeDif = initTimeDif;
        //targetList = new LinkedList<Target>();
        initTime = System.currentTimeMillis();
        mouseX=0;
        mouseY=0;
        time=0;
        timescale=1;
        ptime=0;
        setupUniverse();
        

        anim.start();                                         // Animations-Thread starten
     

     }

    private int dragEvent=0;
    public void mouseDragged(MouseEvent arg0) {
        //throw new UnsupportedOperationException("Not supported yet.");
    setMouse(arg0.getX(),arg0.getY());
    /*if(dragEvent%8==0)addTarget();
    dragEvent++;
    */}

    public void mouseWheelMoved(MouseWheelEvent arg0) {
        int r = arg0.getWheelRotation();
        setTimescale(timescale*Math.pow(2,-r));

    }
    private void setTimescale(double newTimescale) {
    if (newTimescale>1) newTimescale = 1;
    if (newTimescale<1/128.0) newTimescale = 1/128.0;
    //long elapsed = (long)(time*(1000.0/timescale));
    //long falseElapsed = (long)(time*(1000.0/newTimescale));
    //long correction = falseElapsed-elapsed;
    //initTime-=correction;

    timescale = newTimescale;
    }
    
        //System.out.println(arg0.getX() + " " + arg0.g
    public void mouseMoved(MouseEvent arg0) {
        //System.out.println(arg0.getX() + " " + arg0.getY());
        setMouse(arg0.getX(),arg0.getY());
    }

    private void setMouse(int x, int y) {

            double difX = x-(width/2);
            double difY = (height-y) -(height/2);
            mouseX = (difX/width)*(xmax-xmin);
            mouseY = (difY/height)*(ymax-ymin);
    }

    public void mousePressed(MouseEvent arg0) {



     if (arg0.getButton() == 2) {
            originX = mouseX;
            originY = mouseY;
            //luftzeichner = new Luftzeichner(originX,originY);

        } else if (arg0.getButton() == 1) {
        
        //addTarget();


        }// else {luftzeichner.commit();}

    }
    /*private void addTarget() {
                double timeDif = 0.03;
            if (luftzeichner.isEmpty()) timeDif = 1;
            luftzeichner.addTarget(mouseX,mouseY,timeDif);
    }*/
    public void mouseEntered(MouseEvent arg0) {
    }

    public void mouseExited(MouseEvent arg0) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mouseClicked(MouseEvent arg0) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mouseReleased(MouseEvent arg0) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

     public static void main(String[] args)                   // main-Methode der Applikation
     {  new Wurf();
     }


     //  ---------  GL-Events  -----------------------

     public void init(GLAutoDrawable drawable)
     {  GL gl = drawable.getGL();
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);              // erasing color
     }


     public void display(GLAutoDrawable drawable)
     {  GL gl = drawable.getGL();                             // OpenGL Interface
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);                   // Bild loeschen
        gl.glColor3d(1,0,0);                                  // Zeichenfarbe setzen
        zeichneAchsen(gl);
        zeichneBall(gl,mouseX,mouseY);
        time = ((System.currentTimeMillis()-initTime)/(1000000.0/timescale))-ptime;
        //double position = (System.nanoTime()/1000000000.0)*Math.PI;
        //double time = frameTime;

        //Wurfobjekt nw = luftzeichner.release(time);
        /*if (nw != null) {
        objekte.add(nw);
        }*/

        gl.glPolygonMode(gl.GL_FRONT_AND_BACK, gl.GL_FILL);   // Polygon Fuell-Modus
        drawUniverse(gl);

        universe.iterate(time);
        sun.rotate(0.1,new Vector3D(0,0,1));
        //gl.glColor3d(Math.random(),0,0);
        //HashSet<Wurfobjekt> removeList = new HashSet<Wurfobjekt>();
        /*for (Wurfobjekt w : objekte) {

        if (!zeichneBall(gl, w, time)) {
        removeList.add(w);
        }
        }
        for (Wurfobjekt w : removeList) {
        objekte.remove(w);
        }*/

        
        /*if (!lightOn)  {
        zeichneRotation(gl, s,position, false);
        gl.glColor3d(1,1,0);
        gl.glPolygonMode(gl.GL_FRONT_AND_BACK, gl.GL_LINE);   // Polgon Linien-Modus
        zeichneRotation(gl, s, position, false);                                // Randlinien
        }*/

        /*gl.glPolygonMode(gl.GL_FRONT_AND_BACK, gl.GL_FILL);   // Polygon Fuell-Modus
        gl.glColor3d(helligkeit,0,0);*/                         // Zeichenfarbe setzen
        
        /*zeichneRotation(gl, s,position, true);
        gl.glColor3d(1,1,0);
        gl.glPolygonMode(gl.GL_FRONT_AND_BACK, gl.GL_LINE);   // Polgon Linien-Modus
        zeichneRotation(gl, s, position, true);*/


        /*if ( !lightOn )
        {  helligkeit -= 0.0002;                              // verdunkeln
           if ( helligkeit < 0 )
             helligkeit = 0;
        }*/
        ptime=time;
        frameTime++;
     }
     public void drawUniverse(GL gl) {
     drawObject(gl, universe);
     }
     public void drawObject(GL gl, SpaceNode node) {
     Vector3D position = node.getAbsoluteTransformation().getPosition();
         zeichneBall(gl,position.getX(),position.getY());
     for (SpaceNode child : node.getChildren()) {
     drawObject(gl, child);
     }
     }

     public void reshape(GLAutoDrawable drawable,             // Window resized
                         int x, int y,
                         int width, int height)
     {
        this.width = width;
        this.height = height;
        double yxRatio = (float)height/width;                 // aspect-ratio
        ymin = yxRatio*xmin;
        ymax = yxRatio*xmax;
        GL gl = drawable.getGL();
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(xmin, xmax, ymin, ymax, -1, 1);            // Viewing-Volume
        gl.glViewport(0, 0, width, height);              
     }


     public void displayChanged(GLAutoDrawable drawable,
                                boolean modeChanged,
                                boolean deviceChanged) { }


     //  ---------  Window-Events  --------------------

     public void windowClosing(WindowEvent e)
     {  anim.stop();
        System.exit(0);
     }
     
     public void windowActivated(WindowEvent e) {  }
     public void windowClosed(WindowEvent e) {  }
     public void windowDeactivated(WindowEvent e) {  }
     public void windowDeiconified(WindowEvent e) {  }
     public void windowIconified(WindowEvent e) {  }
     public void windowOpened(WindowEvent e) {  }
         
   
     //  ---------  Button-Events  --------------------
     
     public void actionPerformed(ActionEvent e)
     {  lightOn = !lightOn;
        if ( lightOn )
          helligkeit = 1;
     }

     private void setupUniverse() {
     universe = new Universe();
     sun = new SunSystem(universe, 400,0);
     PlanetSystem earth = new PlanetSystem(universe,1,2, new Vector3D(1,1,0));
     PlanetSystem mars = new PlanetSystem(sun,1,2, new Vector3D(-3,0.6,0));
     sun.scale(new Vector3D(0.01,0.01,0.01));
     sun.rotate(30, new Vector3D(0,0,1));
     earth.move(new Vector3D(-6,-6,0));
     mars.move(new Vector3D(3,0.3,0));

     }
         
}  
