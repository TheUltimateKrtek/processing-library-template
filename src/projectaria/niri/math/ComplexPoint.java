package projectaria.niri.math;

public final class ComplexPoint{
	public double x, y;

	public ComplexPoint(double x, double y){
		this.x = x;
		this.y = y;
	}

	public ComplexPoint(){
		this.x = 0;
		this.y = 0;
	}
	
	public ComplexPoint copy(){
		return new ComplexPoint(this.x, this.y);
	}
	public ComplexPoint set(double x, double y){
		this.x = x;
		this.y = y;
		return this;
	}
	public ComplexPoint set(ComplexPoint p){
		if(p == null) p = new ComplexPoint();
		return this.set(p.x, p.y);
	}
	
	public ComplexPoint add(double x, double y){
		this.x += x;
		this.y += y;
		return this;
	}
	public ComplexPoint add(ComplexPoint p){
		if(p == null) return this;
		return this.add(p.x, p.y);
	}
	static public ComplexPoint add(double x1, double y1, double x2, double y2){
		return new ComplexPoint(x1, y1).add(x2, y2);
	}
	static public ComplexPoint add(ComplexPoint p1, double x2, double y2){
		if(p1 == null) return new ComplexPoint();
		return p1.copy().add(x2, y2);
	}
	static public ComplexPoint add(double x1, double y1, ComplexPoint p2){
		if(p2 == null) return new ComplexPoint();
		return new ComplexPoint(x1, y1).add(p2);
	}
	static public ComplexPoint add(ComplexPoint p1, ComplexPoint p2){
		if(p1 == null){
			if(p2 == null) return new ComplexPoint();
			return p2.copy();
		}
		if(p2 == null) return p1.copy();
		return p1.copy().add(p2);
	}

	public ComplexPoint subtract(double x, double y){
		this.x -= x;
		this.y -= y;
		return this;
	}
	public ComplexPoint subtract(ComplexPoint p){
		if(p == null) return this;
		return this.subtract(p.x, p.y);
	}
	static public ComplexPoint subtract(double x1, double y1, double x2, double y2){
		return new ComplexPoint(x1, y1).subtract(x2, y2);
	}
	static public ComplexPoint subtract(ComplexPoint p1, double x2, double y2){
		if(p1 == null) return new ComplexPoint();
		return p1.copy().subtract(x2, y2);
	}
	static public ComplexPoint subtract(double x1, double y1, ComplexPoint p2){
		if(p2 == null) return new ComplexPoint();
		return new ComplexPoint(x1, y1).subtract(p2);
	}
	static public ComplexPoint subtract(ComplexPoint p1, ComplexPoint p2){
		if(p1 == null){
			if(p2 == null) return new ComplexPoint();
			return p2.copy();
		}
		if(p2 == null) return p1.copy();
		return p1.copy().subtract(p2);
	}

	public ComplexPoint multiply(double x, double y){
		return this.set(this.x * x - this.y * y, this.x * y + this.y * x);
	}
	public ComplexPoint multiply(ComplexPoint p){
		if(p == null) return this;
		return this.multiply(p.x, p.y);
	}
	static public ComplexPoint multiply(double x1, double y1, double x2, double y2){
		return new ComplexPoint(x1, y1).multiply(x2, y2);
	}
	static public ComplexPoint multiply(ComplexPoint p1, double x2, double y2){
		if(p1 == null) return new ComplexPoint();
		return p1.copy().multiply(x2, y2);
	}
	static public ComplexPoint multiply(double x1, double y1, ComplexPoint p2){
		if(p2 == null) return new ComplexPoint();
		return new ComplexPoint(x1, y1).multiply(p2);
	}
	static public ComplexPoint multiply(ComplexPoint p1, ComplexPoint p2){
		if(p1 == null){
			if(p2 == null) return new ComplexPoint();
			return p2.copy();
		}
		if(p2 == null) return p1.copy();
		return p1.copy().multiply(p2);
	}

	public ComplexPoint divide(double x, double y){
		double div = x * x + y * y;
		return this.set((this.x * x + this.y * y) / div, (this.y * x - this.x * y) / div);
	}
	public ComplexPoint divide(ComplexPoint p){
		if(p == null) return this;
		return this.divide(p.x, p.y);
	}
	static public ComplexPoint divide(double x1, double y1, double x2, double y2){
		return new ComplexPoint(x1, y1).divide(x2, y2);
	}
	static public ComplexPoint divide(ComplexPoint p1, double x2, double y2){
		if(p1 == null) return new ComplexPoint();
		return p1.copy().divide(x2, y2);
	}
	static public ComplexPoint divide(double x1, double y1, ComplexPoint p2){
		if(p2 == null) return new ComplexPoint();
		return new ComplexPoint(x1, y1).divide(p2);
	}
	static public ComplexPoint divide(ComplexPoint p1, ComplexPoint p2){
		if(p1 == null){
			if(p2 == null) return new ComplexPoint();
			return p2.copy();
		}
		if(p2 == null) return p1.copy();
		return p1.copy().divide(p2);
	}

	public ComplexPoint scale(double x, double y){
		this.x *= x;
		this.y *= y;
		return this;
	}
	public ComplexPoint scale(ComplexPoint p){
		if(p == null) return this;
		return this.scale(p.x, p.y);
	}
	public ComplexPoint scale(double s){
		return this.scale(s, s);
	}
	static public ComplexPoint scale(double x, double y, double xs, double ys){
		return new ComplexPoint(x, y).scale(xs, ys);
	}
	static public ComplexPoint scale(double x, double y, ComplexPoint s){
		return new ComplexPoint(x, y).scale(s);
	}
	static public ComplexPoint scale(double x, double y, double s){
		return new ComplexPoint(x, y).scale(s);
	}
	static public ComplexPoint scale(ComplexPoint p, double xs, double ys){
		if(p == null) return new ComplexPoint();
		return p.copy().scale(xs, ys);
	}
	static public ComplexPoint scale(ComplexPoint p, ComplexPoint s){
		if(p == null) return new ComplexPoint();
		return p.copy().scale(s);
	}
	static public ComplexPoint scale(ComplexPoint p, double s){
		if(p == null) return new ComplexPoint();
		return p.copy().scale(s);
	}

	public ComplexPoint pow(double x, double y){
		double dsq = this.x * this.x + this.y * this.y;
		double arg = Math.atan2(this.y, this.x);

		double anglearg = x * arg + 0.5 * y * Math.log(dsq);
		double mult = Math.pow(dsq, x * 0.5) * Math.exp(- y * arg);

		return this.set(mult * Math.cos(anglearg), mult * Math.sin(anglearg));
	}
	public ComplexPoint pow(ComplexPoint p){
		if(p == null) return this;
		return this.pow(p.x, p.y);
	}
	static public ComplexPoint pow(double x1, double y1, double x2, double y2){
		return new ComplexPoint(x1, y1).pow(x2, y2);
	}
	static public ComplexPoint pow(ComplexPoint p1, double x2, double y2){
		if(p1 == null) return new ComplexPoint();
		return p1.copy().pow(x2, y2);
	}
	static public ComplexPoint pow(double x1, double y1, ComplexPoint p2){
		if(p2 == null) return new ComplexPoint();
		return new ComplexPoint(x1, y1).pow(p2);
	}
	static public ComplexPoint pow(ComplexPoint p1, ComplexPoint p2){
		if(p1 == null){
			if(p2 == null) return new ComplexPoint();
			return p2.copy();
		}
		if(p2 == null) return p1.copy();
		return p1.copy().pow(p2);
	}
	public ComplexPoint sq(){
		return this.set(this.x * this.x - this.y * this.y, 2 * this.x * this.y);
	}
	static public ComplexPoint sq(ComplexPoint p){
		if(p == null) return new ComplexPoint();
		return p.copy().sq();
	}
	static public ComplexPoint sq(double x, double y){
		return new ComplexPoint(x, y).sq();
	}
	public ComplexPoint sqrt(){
		double anglearg = 0.5 * Math.atan2(this.y, this.x);
		double mult = Math.pow(this.x * this.x + this.y * this.y, 0.25);

		return this.set(mult * Math.cos(anglearg), mult * Math.sin(anglearg));
	}
	static public ComplexPoint sqrt(ComplexPoint p){
		if(p == null) return new ComplexPoint();
		return p.copy().sqrt();
	}
	static public ComplexPoint sqrt(double x, double y){
		return new ComplexPoint(x, y).sqrt();
	}
	public ComplexPoint exp(){
		return this.set(new ComplexPoint(Math.cos(this.y), Math.sin(this.y)).scale(Math.exp(this.x)));
	}
	static public ComplexPoint exp(ComplexPoint p){
		if(p == null) return new ComplexPoint();
		return p.copy().exp();
	}
	static public ComplexPoint exp(double x, double y){
		return new ComplexPoint(x, y).exp();
	}
	public ComplexPoint log(){
		return this.set(Math.log(this.getDistanceFromOrigin()) * 0.5, this.getComplexArgument());
	}
	static public ComplexPoint log(double x, double y){
		return new ComplexPoint(x, y).log();
	}
	static public ComplexPoint log(ComplexPoint p){
		if(p == null) return new ComplexPoint();
		return p.copy().log();
	}
	
	public ComplexPoint reciprocal(){
		double div = 1 / (this.x * this.x + this.y * this.y);
		return this.set(this.x * div, - this.y * div);
	}
	static public ComplexPoint reciprocal(double x, double y){
		return new ComplexPoint(x, y).reciprocal();
	}
	static public ComplexPoint reciprocal(ComplexPoint p){
		if(p == null) throw new ArithmeticException("Cannot divide by 0 + 0i");
		return p.copy().reciprocal();
	}
	public ComplexPoint invert(){
		return this.set(- this.x, - this.y);
	}
	static public ComplexPoint invert(double x, double y){
		return new ComplexPoint(x, y).invert();
	}
	static public ComplexPoint invert(ComplexPoint p){
		if(p == null) return new ComplexPoint();
		return p.copy().invert();
	}
	public ComplexPoint conjugate(){
		return this.set(this.x, - this.y);
	}
	static public ComplexPoint conjugate(double x, double y){
		return new ComplexPoint(x, y).conjugate();
	}
	static public ComplexPoint conjugate(ComplexPoint p){
		if(p == null) return new ComplexPoint();
		return p.copy().conjugate();
	}
	
	public ComplexPoint abs(){
		return this.set(this.getDistanceFromOrigin(), 0);
	}
	static public ComplexPoint abs(double x, double y){
		return new ComplexPoint(x, y).abs();
	}
	static public ComplexPoint abs(ComplexPoint p){
		if(p == null) return new ComplexPoint();
		return p.copy().abs();
	}
	public ComplexPoint floor(){
		return this.set(Math.floor(this.x), Math.floor(this.y));
	}
	static public ComplexPoint floor(double x, double y){
		return new ComplexPoint(x, y).floor();
	}
	static public ComplexPoint floor(ComplexPoint p){
		if(p == null) return new ComplexPoint();
		return p.copy().floor();
	}
	public ComplexPoint ceil(){
		return this.set(Math.ceil(this.x), Math.ceil(this.y));
	}
	static public ComplexPoint ceil(double x, double y){
		return new ComplexPoint(x, y).ceil();
	}
	static public ComplexPoint ceil(ComplexPoint p){
		if(p == null) return new ComplexPoint();
		return p.copy().ceil();
	}
	public ComplexPoint round(){
		return this.set(Math.round(this.x), Math.round(this.y));
	}
	static public ComplexPoint round(double x, double y){
		return new ComplexPoint(x, y).round();
	}
	static public ComplexPoint round(ComplexPoint p){
		if(p == null) return new ComplexPoint();
		return p.copy().round();
	}
	
	public ComplexPoint sin(){
		ComplexPoint etoiz = new ComplexPoint(- this.y, this.x).exp();
		ComplexPoint etonegiz = new ComplexPoint(this.y, - this.x).exp();
		return this.set(etoiz.subtract(etonegiz).multiply(0, - 0.5));
	}
	static public ComplexPoint sin(double x, double y){
		return new ComplexPoint(x, y).sin();
	}
	static public ComplexPoint sin(ComplexPoint p){
		if(p == null) return new ComplexPoint();
		return p.copy().sin();
	}
	public ComplexPoint cos(){
		ComplexPoint etoiz = new ComplexPoint(- this.y, this.x).exp();
		ComplexPoint etonegiz = new ComplexPoint(this.y, - this.x).exp();
		return this.set(etoiz.add(etonegiz).multiply(0.5, 0));
	}
	static public ComplexPoint cos(double x, double y){
		return new ComplexPoint(x, y).cos();
	}
	static public ComplexPoint cos(ComplexPoint p){
		if(p == null) return new ComplexPoint();
		return p.copy().cos();
	}
	public ComplexPoint tan(){
		return this.set(this.copy().sin().divide(this.copy().cos()));
	}
	static public ComplexPoint tan(double x, double y){
		return new ComplexPoint(x, y).tan();
	}
	static public ComplexPoint tan(ComplexPoint p){
		if(p == null) return new ComplexPoint();
		return p.copy().tan();
	}
	
	public ComplexPoint asin(){
		ComplexPoint iz = new ComplexPoint(- this.y, this.x);
		ComplexPoint sqrt = new ComplexPoint(1, 0).subtract(this.copy().sq()).pow(0.5, 0);
		return this.set(iz.add(sqrt).log().multiply(0, - 1));
	}
	static public ComplexPoint asin(double x, double y){
		return new ComplexPoint(x, y).asin();
	}
	static public ComplexPoint asin(ComplexPoint p){
		if(p == null) return new ComplexPoint();
		return p.copy().asin();
	}
	public ComplexPoint acos(){
		ComplexPoint thisSq = this.copy().sq();
		ComplexPoint sqrt = new ComplexPoint(1, 0).subtract(thisSq).sqrt();
		ComplexPoint iz = new ComplexPoint(- this.y, this.x);
		
		ComplexPoint res = new ComplexPoint(0, 1).multiply(iz.add(sqrt).log()).add(Math.PI / 2, 0);

		return res;
	}
	static public ComplexPoint acos(double x, double y){
		return new ComplexPoint(x, y).acos();
	}
	static public ComplexPoint acos(ComplexPoint p){
		if(p == null) return new ComplexPoint();
		return p.copy().acos();
	}
	public ComplexPoint atan(){
		ComplexPoint arg1 = new ComplexPoint(1, 0).subtract(- this.y, this.x).log();
		ComplexPoint arg2 = new ComplexPoint(1, 0).add(- this.y, this.x).log();
		ComplexPoint result = new ComplexPoint(0, 0.5).multiply(arg1.subtract(arg2));
		return result;
	}
	static public ComplexPoint atan(double x, double y){
		return new ComplexPoint(x, y).atan();
	}
	static public ComplexPoint atan(ComplexPoint p){
		if(p == null) return new ComplexPoint();
		return p.copy().atan();
	}
	
	public ComplexPoint normalize(){
		double dist = 1 / this.getDistanceFromOrigin();
		return this.set(this.x / dist, this.y * dist);
	}
	static public ComplexPoint normalize(double x, double y){
		return new ComplexPoint(x, y).normalize();
	}
	static public ComplexPoint normalize(ComplexPoint p){
		if(p == null) return new ComplexPoint();
		return p.copy().normalize();
	}
	
	public ComplexPoint rotate(double angle){
		return this.rotateAround(0, 0, angle);
	}
	public ComplexPoint rotateAround(double x, double y, double angle){
		ComplexPoint p = this.copy().subtract(x, y);
		p.multiply((double)Math.cos(angle), (double)Math.sin(angle));
		p.add(x, y);
		this.set(p);
		return this;
	}
	public ComplexPoint rotateAround(ComplexPoint ap, double angle){
		if(ap == null) ap = new ComplexPoint(0, 0);
		return this.rotateAround(ap.x, ap.y, angle);
	}
	static public ComplexPoint rotate(double x, double y, double angle){
		return new ComplexPoint(x, y).rotate(angle);
	}
	static public ComplexPoint rotateAround(double x, double y, double ax, double ay, double angle){
		return new ComplexPoint(x, y).rotateAround(new ComplexPoint(ax, ay), angle);
	}
	static public ComplexPoint rotateAround(double x, double y, ComplexPoint ap, double angle){
		if(ap == null) ap = new ComplexPoint(0, 0);
		return new ComplexPoint(x, y).rotateAround(ap.x, ap.y, angle);
	}
	static public ComplexPoint rotate(ComplexPoint p, double angle){
		if(p == null) p = new ComplexPoint();
		return p.copy().rotate(angle);
	}
	static public ComplexPoint rotateAround(ComplexPoint p, double ax, double ay, double angle){
		if(p == null) p = new ComplexPoint();
		return p.copy().rotateAround(new ComplexPoint(ax, ay), angle);
	}
	static public ComplexPoint rotateAround(ComplexPoint p, ComplexPoint ap, double angle){
		if(p == null) p = new ComplexPoint();
		if(ap == null) ap = new ComplexPoint();
		return p.copy().rotateAround(ap.x, ap.y, angle);
	}
	
	public double getAngle(){
		return this.getComplexArgument();
	}
	public double getAngleRelativeTo(double x, double y){
		return this.getAngleRelativeTo(x, y, 0, 0);
	}
	public double getAngleRelativeTo(ComplexPoint p){
		if(p == null) p = new ComplexPoint();
		return this.getAngleRelativeTo(p.x, p.y, 0, 0);
	}
	public double getAngleRelativeTo(double xo, double yo, double xp, double yp){
		if(this.y == 0) return 0;
		
		double at = this.copy().subtract(xo, yo).getComplexArgument();
		double ap = new ComplexPoint(xp, yp).copy().subtract(xo, yo).getComplexArgument();

		if(this.y > yp) return 0 - (at - ap);
		return at - ap;
	}
	public double getAngleRelativeTo(ComplexPoint o, double xp, double yp){
		if(o == null) o = new ComplexPoint();
		return this.getAngleRelativeTo(o.x, o.y, xp, yp);
	}
	public double getAngleRelativeTo(double xo, double yo, ComplexPoint p){
		if(p == null) p = new ComplexPoint();
		return this.getAngleRelativeTo(xo, yo, p.x, p.y);
	}
	public double getAngleRelativeTo(ComplexPoint o, ComplexPoint p){
		if(o == null) o = new ComplexPoint(0, 0);
		if(p == null) p = new ComplexPoint(0, 0);
		return this.getAngleRelativeTo(o.x, o.y, p.x, p.y);
	}
	static public double getAngle(double xs, double ys){
		return new ComplexPoint(xs, ys).getComplexArgument();
	}
	static public double getAngleBetween(double xs, double ys, double x, double y){
		return new ComplexPoint(xs, ys).getAngleRelativeTo(x, y, 0, 0);
	}
	static public double getAngleBetween(double xs, double ys, ComplexPoint p){
		if(p == null) p = new ComplexPoint();
		return new ComplexPoint(xs, ys).getAngleRelativeTo(p.x, p.y, 0, 0);
	}
	static public double getAngleBetween(double xs, double ys, double xo, double yo, double xp, double yp){
		if(ys == 0) return 0;
		
		double at = new ComplexPoint(xs, ys).subtract(xo, yo).getComplexArgument();
		double ap = new ComplexPoint(xp, yp).copy().subtract(xo, yo).getComplexArgument();

		if(ys > yp) return 0 - (at - ap);
		return at - ap;
	}
	static public double getAngleBetween(double xs, double ys, ComplexPoint o, double xp, double yp){
		if(o == null) o = new ComplexPoint();
		return new ComplexPoint(xs, ys).getAngleRelativeTo(o.x, o.y, xp, yp);
	}
	static public double getAngleBetween(double xs, double ys, double xo, double yo, ComplexPoint p){
		if(p == null) p = new ComplexPoint();
		return new ComplexPoint(xs, ys).getAngleRelativeTo(xo, yo, p.x, p.y);
	}
	static public double getAngleBetween(double xs, double ys, ComplexPoint o, ComplexPoint p){
		if(o == null) o = new ComplexPoint(0, 0);
		if(p == null) p = new ComplexPoint(0, 0);
		return new ComplexPoint(xs, ys).getAngleRelativeTo(o.x, o.y, p.x, p.y);
	}
	static public double getAngle(ComplexPoint s){
		if(s == null) s = new ComplexPoint();
		return ComplexPoint.getAngle(s.x, s.y);
	}
	static public double getAngleBetween(ComplexPoint s, double x, double y){
		if(s == null) s = new ComplexPoint();
		return ComplexPoint.getAngleBetween(s.x, s.y, x, y);
	}
	static public double getAngleBetween(ComplexPoint s, ComplexPoint p){
		if(s == null) s = new ComplexPoint();
		return ComplexPoint.getAngleBetween(s.x, s.y, p);
	}
	static public double getAngleBetween(ComplexPoint s, double xo, double yo, double xp, double yp){
		if(s == null) s = new ComplexPoint();
		return ComplexPoint.getAngleBetween(s.x, s.y, xo, yo, xp, yp);
	}
	static public double getAngleBetween(ComplexPoint s, ComplexPoint o, double xp, double yp){
		if(s == null) s = new ComplexPoint();
		return ComplexPoint.getAngleBetween(s.x, s.y, o, xp, yp);
	}
	static public double getAngleBetween(ComplexPoint s, double xo, double yo, ComplexPoint p){
		if(s == null) s = new ComplexPoint();
		return ComplexPoint.getAngleBetween(s.x, s.y, xo, yo, p);
	}
	static public double getAngleBetween(ComplexPoint s, ComplexPoint o, ComplexPoint p){
		if(s == null) s = new ComplexPoint();
		return ComplexPoint.getAngleBetween(s.x, s.y, o, p);
	}
	
	public double getDistanceFromOriginSq(){
		return this.getDistanceFromSq(0, 0);
	}
	public double getDistanceFromSq(double x, double y){
		double xr = this.x - x;
		double yr = this.y - y;
		return xr * xr + yr * yr;
	}
	public double getDistanceFromSq(ComplexPoint p){
		if(p == null) p = new ComplexPoint();
		return this.getDistanceFrom(p.x, p.y);
	}
	public double getDistanceFromOrigin(){
		return this.getDistanceFrom(0, 0);
	}
	public double getDistanceFrom(double x, double y){
		return this.getDistanceFromSq(x, y);
	}
	public double getDistanceFrom(ComplexPoint p){
		return Math.sqrt(this.getDistanceFromSq(p));
	}
	static public double getDistanceFromOrigin(double x, double y){
		return new ComplexPoint(x, y).getDistanceFromOrigin();
	}
	static public double getDistanceFromOrigin(ComplexPoint p){
		if(p == null) return 0;
		return p.copy().getDistanceFromOrigin();
	}
	static public double getDistanceBetween(double x1, double y1, double x2, double y2){
		return new ComplexPoint(x1, y1).getDistanceFrom(x2, y2);
	}
	static public double getDistanceBetween(ComplexPoint p1, double x2, double y2){
		if(p1 == null) p1 = new ComplexPoint();
		return p1.copy().getDistanceFrom(x2, y2);
	}
	static public double getDistanceBetween(double x1, double y1, ComplexPoint p2){
		if(p2 == null) p2 = new ComplexPoint();
		return p2.copy().getDistanceFrom(x1, y1);
	}
	static public double getDistanceBetween(ComplexPoint p1, ComplexPoint p2){
		if(p1 == null) p1 = new ComplexPoint();
		if(p2 == null) p2 = new ComplexPoint();
		return p1.copy().getDistanceFrom(p2);
	}
	

	public double getComplexArgument(){
		if(this.y == 0 && this.x == 0) return 0;
		return Math.atan2(this.y, this.x);
	}
	public String toString(){
		return this.x + (this.y < 0 ? " - " : " + ") + Math.abs(this.y) + " i";
	}

}