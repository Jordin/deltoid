package in.jord.deltoid.region;

import com.fasterxml.jackson.annotation.JsonProperty;
import in.jord.deltoid.utils.UnionUtilities;
import in.jord.deltoid.vector.Vec2;

import java.util.List;
import java.util.Objects;

public class CircleRegion implements Region<CircleRegion, Vec2> {
    /**
     * A {@link CircleRegion} with coordinates <b>[0, 0]</b> and radius <b>r = 0.0</b>.
     */
    public static final CircleRegion ORIGIN = new CircleRegion(Vec2.ORIGIN, 0);

    /**
     * This is used to represent an invalid or "null" value being returned from a function or similar.
     */
    public static final CircleRegion INVALID = new CircleRegion(Vec2.INVALID, 0);

    private static final double HALF_TAU = Math.PI;

    /**
     * The location of the centre of this {@link CircleRegion}.
     *
     * @serial
     */
    @JsonProperty("centre")
    public final Vec2 centre;

    /**
     * The radius of this {@link CircleRegion}.
     *
     * @serial
     */
    @JsonProperty("radius")
    public final double radius;

    /**
     * The total surface area of this {@link CircleRegion}.
     *
     * @serial
     */
    @JsonProperty("area")
    private double surfaceArea;

    public CircleRegion(Vec2 centre, double radius) {
        this.centre = centre;
        this.radius = radius;

        if (centre.isValid()) {
            this.surfaceArea = HALF_TAU * radius * radius;
        } else {
            this.surfaceArea = 0;
        }
    }

    /**
     * Returns the total volume enclosed in this {@link CircleRegion}.
     *
     * @return <b>0.0</b>, Circles do not have a volume!
     */
    @Override
    public double volume() {
        return 0.0;
    }

    /**
     * Returns the total area of this {@link CircleRegion}.
     *
     * @return the total area of this {@link CircleRegion}
     */
    @Override
    public double surfaceArea() {
        return this.surfaceArea;
    }

    /**
     * Returns {@code true} if the {@link CircleRegion} has
     * a non-zero area, {@code false} otherwise.
     *
     * @return {@code true} if the {@link CircleRegion} exists, {@code false} otherwise
     */
    @Override
    public boolean exists() {
        return this.surfaceArea != 0;
    }

    /**
     * Returns {@code true} if the {@link CircleRegion}
     * contains <b>location</b> {@code false} otherwise.
     *
     * @param location the {@link Vec2} to consider.
     * @return {@code true} if the {@link CircleRegion} contains <b>location</b>, {@code false} otherwise
     */
    @Override
    public boolean contains(Vec2 location) {
        return this.centre.subtract(location.add(Vec2.CENTRE)).length() <= this.radius;
    }

    /**
     * Returns a {@link List} of all of the {@link Vec2}s enclosed in this {@link CircleRegion}.
     *
     * @return the {@link List} of the {@link Vec2}s
     */
    @Override
    public List<Vec2> enclosedPoints() {
        Vec2 bounds = new Vec2(this.radius, this.radius);
        return UnionUtilities.overlap(new RectangleRegion(this.centre.add(bounds), this.centre.subtract(bounds)), this);
    }

    /**
     * Creates the smallest possible {@link CircleRegion}
     * that fully encloses this {@link CircleRegion} and <b>region</b>.
     *
     * @param region the other {@link CircleRegion} to be considered
     * @return the {@link CircleRegion}
     */
    @Override
    public CircleRegion union(CircleRegion region) {
        return UnionUtilities.union(this, region);
    }

    /**
     * Returns a new {@link CircleRegion} translated by <b>offset</b>.
     *
     * @return the new {@link CircleRegion}
     */
    @Override
    public CircleRegion offset(Vec2 offset) {
        return new CircleRegion(this.centre.add(offset), this.radius);
    }

    /**
     * Compares this {@link CircleRegion} to the specified object.  The result is {@code
     * true} if and only if the argument is not {@code null} and is a {@link CircleRegion}
     * object that represents the same rotation angles as this {@link CircleRegion}.
     *
     * @param other the object to compare this {@link CircleRegion} against
     * @return {@code true} if the given object represents a {@link CircleRegion}
     * equivalent to this {@link CircleRegion}, {@code false} otherwise
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || this.getClass() != object.getClass()) return false;

        CircleRegion other = (CircleRegion) object;
        return Double.compare(other.radius, this.radius) == 0 &&
               Objects.equals(this.centre, other.centre);
    }

    @Override
    public int hashCode() {
        return this.centre.hashCode() * 31 + Double.hashCode(this.radius);
    }
}
