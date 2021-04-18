package projectaria.niri.math;

import projectaria.niri.math.ComplexPoint;

public final class Quaternion{
  public double x, y, z, w;

  public Quaternion(){
    this.x = 0;
    this.y = 0;
    this.z = 0;
    this.w = 0;
  }
  public Quaternion(double x, double y, double z, double w){
    this.x = x;
    this.y = y;
    this.z = z;
    this.w = w;
  }

  public Quaternion copy(){
    return new Quaternion(this.x, this.y, this.z, this.w);
  }
  public Quaternion set(double x, double y, double z, double w){
    this.x = x;
    this.y = y;
    this.z = z;
    this.w = w;
    return this;
  }
  public Quaternion set(Quaternion q){
    if(q == null) q = new Quaternion();
		return this.set(q.x, q.y, q.z, q.w);
  }

  public ComplexPoint getComplexPointForPlaneRealI(){
    return new ComplexPoint(w, x);
  }
  public ComplexPoint getComplexPointForPlaneRealJ(){
    return new ComplexPoint(w, y);
  }
  public ComplexPoint getComplexPointForPlaneRealK(){
    return new ComplexPoint(w, z);
  }

  public Quaternion add(double x, double y, double z, double w){
		this.x += x;
		this.y += y;
		this.z += z;
		this.w += w;
		return this;
	}
	public Quaternion add(Quaternion q){
		if(q == null) return this;
		return this.add(q.x, q.y, q.z, q.w);
	}
	static public Quaternion add(double x1, double y1, double z1, double w1, double x2, double y2, double z2, double w2){
		return new Quaternion(x1, y1, z1, w1).add(x2, y2, z2, w2);
	}
	static public Quaternion add(Quaternion q1, double x2, double y2, double z2, double w2){
		if(q1 == null) return new Quaternion();
		return new Quaternion(x2, y2, z2, w2).add(q1);
	}
	static public Quaternion add(double x1, double y1, double z1, double w1, Quaternion q2){
		if(q2 == null) return new Quaternion();
		return new Quaternion(x1, y1, z1, w1).add(q2);
	}
	static public Quaternion add(Quaternion q1, Quaternion q2){
		if(q1 == null){
			if(q2 == null) return new Quaternion();
			return q2.copy();
		}
		if(q2 == null) return q1.copy();
		return q1.copy().add(q2);
	}

  public Quaternion subtract(double x, double y, double z, double w){
		this.x -= x;
		this.y -= y;
		this.z -= z;
		this.w -= w;
		return this;
	}
	public Quaternion subtract(Quaternion q){
		if(q == null) return this;
		return this.subtract(q.x, q.y, q.z, q.w);
	}
	static public Quaternion subtract(double x1, double y1, double z1, double w1, double x2, double y2, double z2, double w2){
		return new Quaternion(x1, y1, z1, w1).subtract(x2, y2, z2, w2);
	}
	static public Quaternion subtract(Quaternion q1, double x2, double y2, double z2, double w2){
		if(q1 == null) return new Quaternion();
		return new Quaternion(x2, y2, z2, w2).subtract(q1);
	}
	static public Quaternion subtract(double x1, double y1, double z1, double w1, Quaternion q2){
		if(q2 == null) return new Quaternion();
		return new Quaternion(x1, y1, z1, w1).subtract(q2);
	}
	static public Quaternion subtract(Quaternion q1, Quaternion q2){
		if(q1 == null){
			if(q2 == null) return new Quaternion();
			return q2.copy();
		}
		if(q2 == null) return q1.copy();
		return q1.copy().subtract(q2);
	}

  public Quaternion multiply(double x, double y, double z, double w){
		return this.set(
      this.w * x + this.x * w + this.y * z - this.z * y,
      this.w * y - this.x * z + this.y * w + this.z * x,
      this.w * z + this.x * y - this.y * x + this.z * w,
      this.w * w - this.x * x - this.y * y - this.z * z
    );
	}
	public Quaternion multiply(Quaternion q){
		if(q == null) return this;
		return this.multiply(q.x, q.y, q.z, q.w);
	}
	static public Quaternion multiply(double x1, double y1, double z1, double w1, double x2, double y2, double z2, double w2){
		return new Quaternion(x1, y1, z1, w1).multiply(x2, y2, z2, w2);
	}
	static public Quaternion multiply(Quaternion q1, double x2, double y2, double z2, double w2){
		if(q1 == null) return new Quaternion();
		return q1.copy().multiply(x2, y2, z2, w2);
	}
	static public Quaternion multiply(double x1, double y1, double z1, double w1, Quaternion q2){
		if(q2 == null) return new Quaternion();
		return new Quaternion(x1, y1, z1, w1).multiply(q2);
	}
	static public Quaternion multiply(Quaternion q1, Quaternion q2){
		if(q1 == null){
			if(q2 == null) return new Quaternion();
			return q2.copy();
		}
		if(q2 == null) return q1.copy();
		return q1.copy().multiply(q2);
	}

  public Quaternion divide(double x, double y, double z, double w){
    double div = 1 / (x * x + y * y + z * z + w * w);
		return this.set(
      (this.x * w - this.w * x - this.z * y + this.y * z) * div,
      (this.y * w + this.z * x - this.w * y - this.x * z) * div,
      (this.z * w - this.y * x + this.x * y - this.w * z) * div,
      (this.w * w + this.x * x + this.y * y + this.z * z) * div
    );
	}
	public Quaternion divide(Quaternion q){
		if(q == null) return this;
		return this.divide(q.x, q.y, q.z, q.w);
	}
	static public Quaternion divide(double x1, double y1, double z1, double w1, double x2, double y2, double z2, double w2){
		return new Quaternion(x1, y1, z1, w1).divide(x2, y2, z2, w2);
	}
	static public Quaternion divide(Quaternion q1, double x2, double y2, double z2, double w2){
		if(q1 == null) return new Quaternion();
		return q1.copy().divide(x2, y2, z2, w2);
	}
	static public Quaternion divide(double x1, double y1, double z1, double w1, Quaternion q2){
		if(q2 == null) return new Quaternion();
		return new Quaternion(x1, y1, z1, w1).divide(q2);
	}
	static public Quaternion divide(Quaternion q1, Quaternion q2){
		if(q1 == null){
			if(q2 == null) return new Quaternion();
			return q2.copy();
		}
		if(q2 == null) return q1.copy();
		return q1.copy().divide(q2);
	}

  public Quaternion scale(double x, double y, double z, double w){
		return this.set(
      this.x * x,
      this.y * y,
      this.z * z,
      this.w * w
    );
	}
	public Quaternion scale(Quaternion q){
		if(q == null) return this;
		return this.scale(q.x, q.y, q.z, q.w);
	}
	public Quaternion scale(double s){
		return this.scale(s, s, s, s);
	}
	static public Quaternion scale(double x1, double y1, double z1, double w1, double x2, double y2, double z2, double w2){
		return new Quaternion(x1, y1, z1, w1).scale(x2, y2, z2, w2);
	}
	static public Quaternion scale(Quaternion q1, double x2, double y2, double z2, double w2){
		if(q1 == null) return new Quaternion();
		return new Quaternion(x2, y2, z2, w2).scale(q1);
	}
	static public Quaternion scale(double x1, double y1, double z1, double w1, double s){
		return new Quaternion(x1, y1, z1, w1).scale(s);
	}
	static public Quaternion scale(double x1, double y1, double z1, double w1, Quaternion q2){
		if(q2 == null) return new Quaternion();
		return new Quaternion(x1, y1, z1, w1).scale(q2);
	}
	static public Quaternion scale(Quaternion q1, Quaternion q2){
		if(q1 == null){
			if(q2 == null) return new Quaternion();
			return q2.copy();
		}
		if(q2 == null) return q1.copy();
		return q1.copy().scale(q2);
	}
  static public Quaternion scale(Quaternion q1, double s){
		if(q1 == null) return new Quaternion();
		return q1.copy().scale(s);
	}
	
  public Quaternion pow(double x, double y, double z, double w){
		double r = this.getDistanceFromOrigin();

		double complexArgumentI = new ComplexPoint(this.w, this.x).getComplexArgument();
		double complexArgumentJ = new ComplexPoint(this.w, this.y).getComplexArgument();
		double complexArgumentK = new ComplexPoint(this.w, this.z).getComplexArgument();

		double angleArgI = Math.log(r) * x + w * complexArgumentI - y * complexArgumentK + z * complexArgumentJ;
		double angleArgJ = Math.log(r) * y + w * complexArgumentJ + x * complexArgumentK - z * complexArgumentI;
		double angleArgK = Math.log(r) * z + w * complexArgumentK - x * complexArgumentK + y * complexArgumentJ;

		double cosI = Math.cos(angleArgI);
		double sinI = Math.sin(angleArgI);
		double cosJ = Math.cos(angleArgJ);
		double sinJ = Math.sin(angleArgJ);
		double cosK = Math.cos(angleArgK);
		double sinK = Math.sin(angleArgK);

		double mult = Math.pow(r, w) * Math.exp(- x * complexArgumentI - y * complexArgumentJ - z * complexArgumentK);

		return this.set(
			mult * (cosI * cosJ * cosK - sinI * sinJ * sinK),
			mult * (sinI * cosJ * cosK + cosI * sinJ * sinK),
			mult * (cosI * sinJ * cosK - sinI * cosJ * sinK),
			mult * (sinI * sinJ * cosK + cosI * cosJ * sinK)
		);
	}
	public Quaternion pow(Quaternion q){
		if(q == null) return this;
		return this.pow(q.x, q.y, q.z, q.w);
	}
	static public Quaternion pow(double x1, double y1, double z1, double w1, double x2, double y2, double z2, double w2){
		return new Quaternion(x1, y1, z1, w1).pow(x2, y2, z2, w2);
	}
	static public Quaternion pow(Quaternion q1, double x2, double z2, double w2, double y2){
		if(q1 == null) return new Quaternion();
		return q1.copy().pow(x2, y2, z2, w2);
	}
	static public Quaternion pow(double x1, double y1, double z1, double w1, Quaternion p2){
		if(p2 == null) return new Quaternion();
		return new Quaternion(x1, y1, z1, w1).pow(p2);
	}
	static public Quaternion pow(Quaternion p1, Quaternion p2){
		if(p1 == null){
			if(p2 == null) return new Quaternion();
			return p2.copy();
		}
		if(p2 == null) return p1.copy();
		return p1.copy().pow(p2);
	}
	
	public Quaternion reciprocal(){
		double div = 1 / (this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w);
		return this.set(- this.x * div, - this.y * div, - this.z * div, this.w * div);
	}
	static public Quaternion reciprocal(double x, double y, double z, double w){
		return new Quaternion(x, y, z, w).reciprocal();
	}
	static public Quaternion reciprocal(Quaternion p){
		if(p == null) throw new ArithmeticException("Cannot divide by 0 + 0i");
		return p.copy().reciprocal();
	}
	public Quaternion invert(){
		return this.set(- this.x, - this.y, - this.z, - this.w);
	}
	static public Quaternion invert(double x, double y, double z, double w){
		return new Quaternion(x, y, z, w).invert();
	}
	static public Quaternion invert(Quaternion p){
		if(p == null) return new Quaternion();
		return p.copy().invert();
	}
	public Quaternion conjugate(){
		return this.set(- this.x, - this.y, - this.z, this.w);
	}
	static public Quaternion conjugate(double x, double y, double z, double w){
		return new Quaternion(x, y, z, w).conjugate();
	}
	static public Quaternion conjugate(Quaternion p){
		if(p == null) return new Quaternion();
		return p.copy().conjugate();
	}
	
  public Quaternion abs(){
		return this.set(0, 0, 0, this.getDistanceFromOrigin());
	}
	static public Quaternion abs(Quaternion p){
		if(p == null) return new Quaternion();
		return p.copy().abs();
	}
	static public Quaternion abs(double x, double y, double z, double w){
		return new Quaternion(x, y, z, w).abs();
	}
	
	public Quaternion floor(){
		return this.set(Math.floor(this.x), Math.floor(this.y), Math.floor(this.z), Math.floor(this.w));
	}
	static public Quaternion floor(double x, double y, double z, double w){
		return new Quaternion(x, y, z, w).floor();
	}
	static public Quaternion floor(Quaternion p){
		if(p == null) return new Quaternion();
		return p.copy().floor();
	}
	public Quaternion ceil(){
		return this.set(Math.ceil(this.x), Math.ceil(this.y), Math.ceil(this.z), Math.ceil(this.w));
	}
	static public Quaternion ceil(double x, double y, double z, double w){
		return new Quaternion(x, y, z, w).ceil();
	}
	static public Quaternion ceil(Quaternion p){
		if(p == null) return new Quaternion();
		return p.copy().ceil();
	}
	public Quaternion round(){
		return this.set(Math.round(this.x), Math.round(this.y), Math.round(this.z), Math.round(this.w));
	}
	static public Quaternion round(double x, double y, double z, double w){
		return new Quaternion(x, y, z, w).round();
	}
	static public Quaternion round(Quaternion p){
		if(p == null) return new Quaternion();
		return p.copy().round();
	}
	
  public Quaternion normalize(){
		return this.scale(1 / this.getDistanceFromOrigin());
	}
	static public Quaternion normalize(double x, double y, double z, double w){
		return new Quaternion(x, y, z, w).normalize();
	}
	static public Quaternion normalize(Quaternion p){
		if(p == null) return new Quaternion();
		return p.copy().normalize();
	}

  public double getDistanceFromOriginSq(){
		return this.getDistanceFromSq(0, 0, 0, 0);
	}
	public double getDistanceFromSq(double x, double y, double z, double w){
		double xr = this.x - x;
		double yr = this.y - y;
		double zr = this.z - z;
		double wr = this.w - w;
		return xr * xr + yr * yr + zr * zr + wr * wr;
	}
	public double getDistanceFromSq(Quaternion p){
		if(p == null) p = new Quaternion();
		return this.getDistanceFrom(p.x, p.y, p.z, p.w);
	}
	public double getDistanceFromOrigin(){
		return this.getDistanceFrom(0, 0, 0, 0);
	}
	public double getDistanceFrom(double x, double y, double z, double w){
		return Math.sqrt(this.getDistanceFromSq(x, y, z, w));
	}
	public double getDistanceFrom(Quaternion p){
		return Math.sqrt(this.getDistanceFromSq(p));
	}
	static public double getDistanceFromOrigin(double x, double y, double z, double w){
		return new Quaternion(x, y, z, w).getDistanceFromOrigin();
	}
	static public double getDistanceFromOrigin(Quaternion p){
		if(p == null) return 0;
		return p.copy().getDistanceFromOrigin();
	}
	static public double getDistanceBetween(double x1, double y1, double z1, double w1, double x2, double y2, double z2, double w2){
		return new Quaternion(x1, y1, z1, w1).getDistanceFrom(x2, y2, z2, w2);
	}
	static public double getDistanceBetween(Quaternion p1, double x2, double y2, double z2, double w2){
		if(p1 == null) p1 = new Quaternion();
		return p1.copy().getDistanceFrom(x2, y2, z2, w2);
	}
	static public double getDistanceBetween(double x1, double y1, double z1, double w1, Quaternion p2){
		if(p2 == null) p2 = new Quaternion();
		return p2.copy().getDistanceFrom(x1, y1, z1, w1);
	}
	static public double getDistanceBetween(Quaternion p1, Quaternion p2){
		if(p1 == null) p1 = new Quaternion();
		if(p2 == null) p2 = new Quaternion();
		return p1.copy().getDistanceFrom(p2);
	}
	
  public String toString(){
    return this.w + (this.x < 0 ? " - " : " + ") + Math.abs(this.x) + " i" + (this.y < 0 ? " - " : " + ") + Math.abs(this.y) + " j" + (this.z < 0 ? " - " : " + ") + Math.abs(this.z) + " k";
  }
}