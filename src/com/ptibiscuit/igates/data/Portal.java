/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ptibiscuit.igates.data;

import com.ptibiscuit.igates.Plugin;
import java.util.ArrayList;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 *
 * @author ANNA
 */
public class Portal {
	private String tag;
	private Location toPoint;
	private ArrayList<Volume> fromPoints;
	private FillType fillType;
	private int price;
	private boolean active;

	public Portal(String tag, Location toPoint, ArrayList<Volume> fromPoints, int price, FillType fillType, boolean active) {
		this.tag = tag;
		this.toPoint = toPoint;
		this.price = price;
		this.fromPoints = fromPoints;
		this.fillType = fillType;
		this.active = active;
	}

	public FillType getFillType() {
		return fillType;
	}

	public int getPrice()
	{
		return price;
	}
	
	public ArrayList<Volume> getFromPoints() {
		return fromPoints;
	}
	
	public String getTag() {
		return tag;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public boolean teleportPlayer(Player p)
	{
		// We check for the price
		if (Plugin.instance.isEconomyEnabled() && this.price != 0 && !Plugin.instance.getPermissionHandler().has(p, "god", true))
		{
			Economy econ = Plugin.instance.getEconomy();
			double actualMoneyOfPlayer = econ.getBalance(p.getName());
			String formatPrice = econ.format(this.price);
			if (actualMoneyOfPlayer >= this.price) {
				Plugin.instance.sendMessage(p, Plugin.instance.getSentence("pay_the_price").replace("{PRICE}", formatPrice));
				econ.withdrawPlayer(p.getName(), this.price);
			} else {
				// Il n'a pas assez d'argent
				Plugin.instance.sendMessage(p, Plugin.instance.getSentence("cant_afford").replace("{PRICE}", formatPrice));
				return false;
			}
		}
		Location l = this.toPoint;
		Chunk c = this.toPoint.getChunk();
		if (!l.getWorld().isChunkLoaded(c))
		{
			l.getWorld().loadChunk(c);
		}
		p.teleport(l);
		return true;
	}
	
	public boolean isIn(Location l, double offset)
	{
		for (Volume v : this.fromPoints)
		{
			// Check "from" world first
			if (v.getWorld() == l.getWorld()) {
				if (v.isIn(l, offset)) return true;
			}
		}
		return false;
	}
	
	public boolean isIn(Location l) {
		return this.isIn(l, 0);
	}
	
	public void setActive(boolean active) {
		this.active = active;
		if (active)
		{
			this.fillBlocks();
		}
		else
		{
			this.defillBlocks();
		}
	}

	public void setFillType(FillType fillType) {
		if (this.isActive())
		{
			this.defillBlocks();
			this.fillType = fillType;
			this.fillBlocks();
		}
	}

	public void beforeDelete()
	{
		this.defillBlocks();
	}
	
	public void defillBlocks()
	{
		for (Volume v : this.fromPoints)
			this.fillType.defillBlocks(v.getBlocks());
	}
	
	public void fillBlocks()
	{
		for (Volume v : this.fromPoints)
			this.fillType.fillBlocks(v.getBlocks());
	}
	
	public void setFromPoints(ArrayList<Volume> fromPoints) {
		this.fromPoints = fromPoints;
	}

	public void setToPoint(Location toPoint) {
		this.toPoint = toPoint;
	}
}
