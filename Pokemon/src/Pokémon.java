
public class Pok¨¦mon {
	
	private int num, stage, experiencePoint, hitPoint, energyPoint, attackPoint, resistancePoint;
	private String type, energyColour, status;
	
	public Pok¨¦mon(int n, String type, int stage, int xp, int hp, int ep, String colour, int ap, int rp, String status) {
		this.num = n;
		this.type = type;
		this.stage = stage;
		experiencePoint = xp;
		hitPoint = hp;
		energyPoint = ep;
		energyColour = colour;
		attackPoint = ap;
		resistancePoint = rp;
		this.status = status;
	}
	
	public int getStage() {
		return stage;
	}

	public void setStage(int stage) {
		this.stage = stage;
	}

	public int getExperiencePoint() {
		return experiencePoint;
	}

	public void setExperiencePoint(int experiencePoint) {
		this.experiencePoint = experiencePoint;
	}

	public int getHitPoint() {
		return hitPoint;
	}

	public void setHitPoint(int hitPoint) {
		this.hitPoint = hitPoint;
	}

	public int getEnergyPoint() {
		return energyPoint;
	}

	public void setEnergyPoint(int energyPoint) {
		this.energyPoint = energyPoint;
	}

	public String getStatus() {
		return status;
	}
	
	public void setEnergyColour(String colour) {
		energyColour = colour;
	}

	public void setAttackPoint(int attackPoint) {
		this.attackPoint = attackPoint;
	}

	public void setResistancePoint(int resistancePoint) {
		this.resistancePoint = resistancePoint;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getNum() {
		return num;
	}

	public int getAttackPoint() {
		return attackPoint;
	}

	public int getResistancePoint() {
		return resistancePoint;
	}

	public String getType() {
		return type;
	}

	public String getEnergyColour() {
		return energyColour;
	}
	
	public String toString() {
		return String.format("©¦%d  ©¦%s\t©¦%d\t©¦%d\t   ©¦%d\t     ©¦%d\t©¦%s\t ©¦%d\t      ©¦%d\t       ©¦%s\t©¦",
				num, type, stage, experiencePoint, hitPoint, energyPoint,
				energyColour, attackPoint, resistancePoint, status);
	}


}
