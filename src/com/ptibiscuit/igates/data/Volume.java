package com.ptibiscuit.igates.data;

import java.util.ArrayList;
import org.bukkit.Location;
import org.bukkit.World;

import com.ptibiscuit.igates.Plugin;

public class Volume {
	private Location first;
	private Location end;
	private World world = null;
	public int maxx, minx, maxy, miny, maxz, minz;
	
	public Volume(Location f, Location e)
	{
		first = f;
		end = e;
		if (first != null) { 
			world = first.getWorld();
			maxx = (first.getBlockX() > end.getBlockX()) ? first.getBlockX() : end.getBlockX();
			minx = (first.getBlockX() < end.getBlockX()) ? first.getBlockX() : end.getBlockX();
			maxy = (first.getBlockY() > end.getBlockY()) ? first.getBlockY() : end.getBlockY();
			miny = (first.getBlockY() < end.getBlockY()) ? first.getBlockY() : end.getBlockY();
			maxz = (first.getBlockZ() > end.getBlockZ()) ? first.getBlockZ() : end.getBlockZ();
			minz = (first.getBlockZ() < end.getBlockZ()) ? first.getBlockZ() : end.getBlockZ();
		}
	}
	
	public ArrayList<Location> getBlocks()
	{
		ArrayList<Location> locs = new ArrayList<Location>();
		for (int fy = miny;fy <= maxy;fy++)
		{
			for (int fx = minx;fx <= maxx;fx++)
			{
				for (int fz = minz;fz <= maxz;fz++)
				{
					locs.add(new Location(first.getWorld(), fx, fy, fz));
				}
			}
		}
		return locs;
	}
	
	public boolean isIn(Location l, double offset)
	{
		// If ANY location axis is outside the same portal axis, abort immediately (fastest method)
		// (it's most likely that the X/Z will be more helpful, so check them first)
		// !!WARNING!! With this method, "offset" no longer works (but was never used anyway)
		if (l.getBlockX() < minx || l.getBlockX() > maxx) return false;
		if (l.getBlockZ() < minz || l.getBlockZ() > maxz) return false;
		if (l.getBlockY() < miny || l.getBlockY() > maxy) return false;

		// Location is within the portal area
		return true;
	}
	
	public boolean isIn(Location l) {
		return this.isIn(l, 0);
	}

	public Location getFirst() {
		return first;
	}

	public void setFirst(Location first) {
		this.first = first;
		if ((first != null) && (end != null)) { 
			world = first.getWorld();
			maxx = (first.getBlockX() > end.getBlockX()) ? first.getBlockX() : end.getBlockX();
			minx = (first.getBlockX() < end.getBlockX()) ? first.getBlockX() : end.getBlockX();
			maxy = (first.getBlockY() > end.getBlockY()) ? first.getBlockY() : end.getBlockY();
			miny = (first.getBlockY() < end.getBlockY()) ? first.getBlockY() : end.getBlockY();
			maxz = (first.getBlockZ() > end.getBlockZ()) ? first.getBlockZ() : end.getBlockZ();
			minz = (first.getBlockZ() < end.getBlockZ()) ? first.getBlockZ() : end.getBlockZ();
		}
	}

	public Location getEnd() {
		return end;
	}

	public void setEnd(Location end) {
		this.end = end;
		if ((first != null) && (end != null)) { 
			world = first.getWorld();
			maxx = (first.getBlockX() > end.getBlockX()) ? first.getBlockX() : end.getBlockX();
			minx = (first.getBlockX() < end.getBlockX()) ? first.getBlockX() : end.getBlockX();
			maxy = (first.getBlockY() > end.getBlockY()) ? first.getBlockY() : end.getBlockY();
			miny = (first.getBlockY() < end.getBlockY()) ? first.getBlockY() : end.getBlockY();
			maxz = (first.getBlockZ() > end.getBlockZ()) ? first.getBlockZ() : end.getBlockZ();
			minz = (first.getBlockZ() < end.getBlockZ()) ? first.getBlockZ() : end.getBlockZ();
		}
	}
	
	public World getWorld() {
		return world;
	}
}
