/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package starchild.coordinate;

/**
 *
 * @author testi
 */
public class Transformation implements Cloneable {
private double[] matrix;
private double[] inverse;

    private Transformation(double[] matrix, double[] inverse) {
    //if (matrix.length!=16) {throw new IllegalArgumentException("Array length must be 16");}
    this.matrix = matrix;
    this.inverse = inverse;
    }
    public double get(int row, int column) {
    return matrix[row*4+column];
    }
    private static double Get(double[] matrix, int row, int column) {
    return matrix[row*4+column];
    }

    public static Transformation Identity() { //Note that this must be newly produced as long as there as methods modifying field values
    

    double[] matrix = IdentityMatrix();
    Transformation t= new Transformation(matrix, CloneArray(matrix));
    return t;


    }

    private static double[] IdentityMatrix() {
    double[] matrix = new double[16];
    for (int i = 0;i<16;i++) {
    if (i%5==0){matrix[i]=1;} else {matrix[i]=0;}
    }
    return matrix;
    }
    /*public static Transformation Empty() { //Note that this must be newly produced as long as there as methods modifying field values
    double[] matrix = new double[16];
    for (int i = 0;i<16;i++) {
    matrix[i]=0;
    }
    return new Transformation(matrix);
    }*/
    public Transformation multiply(Transformation t) {
    double[] newMatrix = new double[16];
    double[] newInverse = new double[16];
    for (int i = 0; i < 16;i++) {
        int row = i/4;
        int col = i%4;
        double result = 0;
        double inverseResult=0;
        for (int j = 0; j<4;j++) {
        result+=get(row,j)*t.get(j, col);
        inverseResult+=Get(inverse,row,j)*Get(t.inverse, j, col);
        }
        newMatrix[i] = result;
        newInverse[i] = inverseResult;
    }
    return new Transformation(newMatrix, newInverse);

    }
    /*public static double[] Multiply(double[] A, double[] B) {
    double[] C = IdentityMatrix();
    //Translation
    C[3]=A[0]*B[3]+A[3];
    C[7]=A[5]*B[7]+A[7];
    C[11]=A[10]*B[11]+A[11];
    //Skalierung
    C[0]=B[0]*A[0];
    C[5]=B[5]*A[5];
    C[10]=B[10]*A[10];
    //Rotation
    }*/
    public Transformation invert() {
    return new Transformation(inverse, matrix);
        
    //double[] o = matrix;
    //Transformation t = Identity();
    //double[] m = t.matrix;

    /*m[0] = o[0];
m[1]=o[4];
m[2]=o[8];
m[3] = -o[0]*o[3]-o[4]*o[7]-o[8]*o[11];
m[4]=o[1];
m[5]=o[5];
m[6]=o[9];
m[7] = -o[1]*o[3]-o[5]*o[7]-o[9]*o[11];
m[8] = o[2];
m[9] = o[6];
m[10] = o[10];
m[11] = -o[2]*o[3]-o[6]*o[7]-o[10]*o[11];
return t;*/

    }
    public Transformation scale(Vector3D scale) {
    Transformation scaleMatrix = Identity();
    scaleMatrix.matrix[0] = scale.getX();
    scaleMatrix.matrix[5] = scale.getY();
    scaleMatrix.matrix[10] = scale.getZ();
    scaleMatrix.inverse[0] = 1/scale.getX();
    scaleMatrix.inverse[5] = 1/scale.getY();
    scaleMatrix.inverse[10] = 1/scale.getZ();
    
    return scaleMatrix.multiply(this);
    /*Transformation t = this.clone();
    t.matrix[0]*=scale.getX();
    t.matrix[5]*=scale.getY();
    t.matrix[10]*=scale.getZ();
    t.inverse[0]/=scale.getX();
    t.inverse[5]/=scale.getY();
    t.inverse[10]/=scale.getZ();
    return t;*/
    }
    public Vector3D getPosition() {
    return ExtractPositionVector(this);
    }
    public Transformation setPosition(Vector3D vector) {
    Transformation t = clone();
    t.matrix[3] = vector.getX();
    t.matrix[7] = vector.getY();
    t.matrix[11] = vector.getZ();
    t.inverse[3] = -vector.getX();
    t.inverse[7] = -vector.getY();
    t.inverse[11] = -vector.getZ();
    return t;
    }
    public Vector3D getTranslationParameters() {
    return new Vector3D(get(0,3),get(1,3),get(2,3));
    }
    public Vector3D getScaleParameters() {
    return getPosition();
    }
    public Transformation clone() {
        double[] cloneMatrix = CloneArray(matrix);
        double[] cloneInverse = CloneArray(inverse);
        return new Transformation(cloneMatrix,cloneInverse);
    }
    private static double[] CloneArray(double[] matrix) {
    double[] cloneMatrix = new double[16];
    for (int i = 0; i< 16; i++) {
    cloneMatrix[i] = matrix[i];
    }
    return cloneMatrix;
    }
    public Transformation translate(Vector3D vector) {
    Transformation translation = Identity();
    translation.matrix[3] = vector.getX();
    translation.matrix[7] = vector.getY();
    translation.matrix[11] = vector.getZ();
    translation.inverse[3] = -vector.getX();
    translation.inverse[7] = -vector.getY();
    translation.inverse[11] = -vector.getZ();
    return translation.multiply(this);
    }
    public Transformation rotate(double degree, Vector3D axis) {
    Vector3D normalized = axis.normalize();
    //double xRotate = degree*normalized.getX();
    //double yRotate = degree*normalized.getY();
    //double zRotate = degree*normalized.getZ();
    //ZYXM
    return Rotation(degree, axis).multiply(this);
    }
    public static Transformation AsTranslateTransformation(Vector3D vector) {
    Transformation t = Identity();
    double[] m = t.matrix;
    double[] i = t.inverse;
    m[3] = vector.getX();
    i[3] = -vector.getX();
    m[7] = vector.getY();
    i[7] = -vector.getY();
    m[11] = vector.getZ();
    i[11] = -vector.getZ();
    //m[15] = 0;
    return t;
    }
    public static Transformation AsScaleTransformation(Vector3D vector) {
        Transformation t = Identity();
    double[] m = t.matrix;
    double[] i = t.inverse;
    m[0] = vector.getX();
    i[0] = -vector.getX();
    m[5] = vector.getY();
    i[5] = -vector.getY();
    m[10] = vector.getZ();
    i[10] = -vector.getZ();
    //m[15] = 0;
    return t;
    }
    public static Vector3D ExtractPositionVector(Transformation t) {
    return new Vector3D(t.get(0, 3),t.get(1, 3),t.get(2, 3));
    }
    public static Transformation Rotation(double degree, Vector3D axis) {
    return new Transformation(RotationMatrix(degree, axis),RotationMatrix(-degree, axis));
    }
  



    public static double[] RotationMatrix(double degree, Vector3D axis) {
    Vector3D normalized = axis.normalize();
    double a = (degree/180.0)*Math.PI;
    double[] m = IdentityMatrix();
    double v1 = normalized.getX();
    double v2 = normalized.getY();
    double v3 = normalized.getZ();
    m[0] = Math.cos(a) + v1*v1*(1-Math.cos(a));
    m[1] = v1*v2*(1-Math.cos(a))-v3*Math.sin(a);
    m[2] = v1*v2*(1-Math.cos(a))+ v2*Math.sin(a);
    m[4] = v2*v1*(1-Math.cos(a))+v3*Math.sin(a);
    m[5] = Math.cos(a) + v2*v2*(1-Math.cos(a));
    m[6] = v2*v3*(1-Math.cos(a))-v1*Math.sin(a);

    m[8] = v3*v1*(1-Math.cos(a))-v2*Math.sin(a);
    m[9] = v3*v2*(1-Math.cos(a)) + v1*Math.sin(a);


    m[10] = Math.cos(a) + v3*v3*(1- Math.cos(a));

    return m;
    }




    public boolean equals(Transformation t) {
    if (this == t)return true;
    if (t == null)return false;
    for (int i = 0; i< 16; i++) {
    if (matrix[i]!=t.matrix[i]){return false;}
    }
    return true;
    }

}
