package in.jord.deltoid.vector;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Vec2 implements Vector<Vec2> {
    /**
     * A {@link Vec2} with all coordinates being {@link Double#NaN}.
     * This is used to represent an invalid or "null" value being returned from a function or similar.
     */
    public static final Vec2 INVALID = new Vec2();

    /**
     * A {@link Vec2} with coordinates <b>[0, 0]</b>.
     */
    public static final Vec2 ORIGIN = new Vec2(0, 0);

    /**
     * A {@link Vec2} with coordinates <b>[1, 1]</b>.
     */
    public static final Vec2 ONE = new Vec2(1, 1);

    /**
     * A unit {@link Vec2} parallel to the <b>x</b>-axis.
     */
    public static final Vec2 X_AXIS = new Vec2(1, 0);

    /**
     * A unit {@link Vec2} parallel to the <b>y</b>-axis.
     */
    public static final Vec2 Y_AXIS = new Vec2(0, 1);

    /**
     * A {@link Vec2} with coordinates <b>[0, 0]</b>.
     */
    public static final Vec2 ZERO = ORIGIN;

    /**
     * A unit {@link Vec2} parallel to the <b>x</b>-axis.
     */
    public static final Vec2 I_HAT = X_AXIS;

    /**
     * A unit {@link Vec2} parallel to the <b>y</b>-axis.
     */
    public static final Vec2 J_HAT = Y_AXIS;

    /**
     * A {@link Vec2} with coordinates <b>[0.5, 0.5]</b>.
     *
     * <p>
     * This represents the position of the midpoint of a <b>1x1</b> surface parallel to the <b>x</b>-<b>y</b> plane
     * </p>
     *
     */
    public static final Vec2 CENTRE = new Vec2(0.5, 0.5);

    /**
     * The <b>x</b>-component of the {@link Vec2}.
     *
     * @serial
     */
    @JsonProperty("x")
    public final double x;

    /**
     * The <b>y</b>-component of the {@link Vec2}.
     *
     * @serial
     */
    @JsonProperty("y")
    public final double y;

    private transient double length = -1;

    /**
     * Constructs a newly allocated {@link Vec2} object.
     *
     * @param x the magnitude of the <b>x</b>-component of the {@link Vec2}.
     * @param y the magnitude of the <b>y</b>-component of the {@link Vec2}.
     */
    public Vec2(double x, double y) {
        if (Double.isNaN(x))
            throw new IllegalArgumentException("x shall not be NaN!");
        if (Double.isNaN(y))
            throw new IllegalArgumentException("y shall not be NaN!");
        this.x = x;
        this.y = y;
    }

    /**
     * Constructs an invalid {@link Vec2} instance.
     *
     * @implNote Generally only one invalid instance, {@link #INVALID},
     * should exist per VM.
     */
    private Vec2() {
        this.x = Double.NaN;
        this.y = Double.NaN;
    }

    private void calculateLength() {
        if (this.length == -1) {
            this.length = Math.sqrt(this.x * this.x + this.y * this.y);
        }
    }

    /**
     * Returns the magnitude of the {@link Vec2}.
     *
     * @return the magnitude of the {@link Vec2}
     */
    @Override
    public double length() {
        this.calculateLength();
        return this.length;
    }

    /**
     * Returns the manhattan (taxicab) length of the {@link Vec2}.
     *
     * @return the manhattan length of the {@link Vec2}
     */
    @Override
    public double manhattan() {
        return Math.abs(this.x) + Math.abs(this.y);
    }

    /**
     * Breaks this {@link Vec2} into its underlying components.
     *
     * @return the components of this {@link Vec2}
     */
    @Override
    public double[] components() {
        return new double[]{this.x, this.y};
    }

    /**
     * Compares this {@link Vec2} to the specified object. The result is {@code
     * true} if and only if the argument is not {@code null} and is a {@link Vec2}
     * object that represents the same coordinates as this {@link Vec2}.
     *
     * @param other the object to compare this {@link Vec2} against
     * @return {@code true} if the given object represents a {@link Vec2}
     * equivalent to this {@link Vec2}, {@code false} otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || this.getClass() != other.getClass()) return false;
        Vec2 vec2 = (Vec2) other;
        return Double.compare(vec2.x, this.x) == 0 &&
               Double.compare(vec2.y, this.y) == 0;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(this.x) * 31 + Double.hashCode(this.y);
    }

    /**
     * Returns the unit {@link Vec2} parallel to this {@link Vec2}.
     *
     * @return the {@link Vec2}
     */
    @Override
    public Vec2 normalize() {
        this.calculateLength();
        if (this.length == 0) {
            return ZERO;
        }
        return new Vec2(this.x / this.length, this.y / this.length);
    }

    /**
     * Returns a scalar multiple of the unit {@link Vec2} parallel to this {@link Vec2}.
     *
     * @param length the desired length of the {@link Vec2}.
     * @return the {@link Vec2}
     */
    @Override
    public Vec2 normalize(double length) {
        return this.normalize().scale(length);
    }

    /**
     * Returns a scalar multiple of this {@link Vec2}.
     *
     * @param scaleFactor the desired scale factor for the {@link Vec2}.
     * @return the {@link Vec2}
     */
    @Override
    public Vec2 scale(double scaleFactor) {
        return new Vec2(this.x * scaleFactor, this.y * scaleFactor);
    }

    /**
     * Returns the {@link Vec2} that is the sum of this {@link Vec2} and {@code addend}.
     *
     * @param addend the {@link Vec2} to be added to this {@link Vec2}.
     * @return the {@link Vec2}
     */
    @Override
    public Vec2 add(Vec2 addend) {
        return new Vec2(this.x + addend.x, this.y + addend.y);
    }

    /**
     * Returns the {@link Vec2} that is the difference of this {@link Vec2} and {@code subtrahend}.
     *
     * @param subtrahend the Vector to be subtracted from this {@link Vec2}.
     * @return the {@link Vec2}
     */
    @Override
    public Vec2 subtract(Vec2 subtrahend) {
        return new Vec2(this.x - subtrahend.x, this.y - subtrahend.y);
    }

    /**
     * Returns the {@link Vec2} that is this {@link Vec2} with floored coordinates.
     *
     * @return the {@link Vec2}
     */
    @Override
    public Vec2 floor() {
        return new Vec2(Math.floor(this.x), Math.floor(this.y));
    }

    /**
     * Returns the {@link Vec2} that is this {@link Vec2} with ceilinged coordinates.
     *
     * @return the {@link Vec2}
     */
    @Override
    public Vec2 ceil() {
        return new Vec2(Math.ceil(this.x), Math.ceil(this.y));
    }

    /**
     * Returns the {@link Vec2} that is anti-parallel to this {@link Vec2}.
     *
     * @return the {@link Vec2}
     */
    @Override
    public Vec2 reverse() {
        return new Vec2(-this.x, -this.y);
    }

    @Override
    public String toString() {
        return String.format("Vec2(x=%f, y=%f)", this.x, this.y);
    }

    public String toSimpleString() {
        return String.format("%.2f %.2f", this.x, this.y);
    }

    /**
     * Construct and return a newly-allocated Vec2 object.
     *
     * @param x the magnitude of the <b>x</b>-component of the {@link Vec2}
     * @param y the magnitude of the <b>y</b>-component of the {@link Vec2}
     * @return new Vec2 instance
     * @see Vec2#Vec2(double, double)
     */
    public static Vec2 of(double x, double y) {
        if (Double.isNaN(x) || Double.isNaN(y)) {
            return INVALID;
        }
        return new Vec2(x, y);
    }

    /**
     * Returns {@code true} IFF this {@link Vec2} is
     * considered to be valid, with each {@code component ∈ ℝ}.
     *
     * @return {@code true} if the {@link Vec2} is valid, {@code false} otherwise
     */
    @Override
    public boolean isValid() {
        return this != INVALID;
    }
}
