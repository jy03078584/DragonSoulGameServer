 /** 
 *
 * @Title: City.java 
 * @Package com.lk.dragon.db.domain 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2014-9-24 下午3:02:16 
 * @version V1.0   
 */
package com.lk.dragon.db.domain;

import java.util.List;

/**  
 * @Description:城市信息表映射
 */
public class City {
	private Long city_id;//城邦id
	private Long role_id;//所属角色id
	private Integer site_x;//x坐标
	private Integer site_y;//y坐标
	private Integer home;//是否为主城,1为主城，0为要塞
	private Integer race;//种族
	private Integer eat;//人口
	private String name;//城邦名称
	
	//城镇产量
    private Integer yield_food;
    private Integer yield_wood;
    private Integer yield_stone;
	private Integer store;
	private Integer loyal;//城邦忠诚度
	private Integer trans_cd;//传送门 冷却结束时间
	
	//角色相关信息
	private String role_name;
	private String role_icon;
	private int role_lev;
	
	//城内已戍守的增援英雄
	private Integer reinforce;
	
	//角色工会信息
	private String faction_name;
	
	//城邦所属角色拥有产量
	private Integer role_food;//角色总粮食产量
	private Integer role_wood;//角色总木材产量
	private Integer role_stone;//角色总石料产量
	private Integer role_eat;
    //被侦察产量
    private int check_wood;
    private int check_food;
    private int check_stone;
    //城市BUFF
    private List<Buff> buffs;
    

    
    
    
    
	public Integer getRole_eat() {
		return role_eat;
	}
	public void setRole_eat(Integer role_eat) {
		this.role_eat = role_eat;
	}
	public List<Buff> getBuffs() {
		return buffs;
	}
	public void setBuffs(List<Buff> buffs) {
		this.buffs = buffs;
	}
	public int getCheck_wood() {
		return check_wood;
	}
	public void setCheck_wood(int check_wood) {
		this.check_wood = check_wood;
	}
	public int getCheck_food() {
		return check_food;
	}
	public void setCheck_food(int check_food) {
		this.check_food = check_food;
	}
	public int getCheck_stone() {
		return check_stone;
	}
	public void setCheck_stone(int check_stone) {
		this.check_stone = check_stone;
	}
	public City(){}


	
	
	
	
	public City(Long role_id, Integer site_x, Integer site_y, Integer home,
			Integer race, Integer eat, String name) {
		super();
		this.role_id = role_id;
		this.site_x = site_x;
		this.site_y = site_y;
		this.home = home;
		this.race = race;
		this.eat = eat;
		this.name = name;
	}






	public Integer getRole_food() {
		return role_food;
	}






	public void setRole_food(Integer role_food) {
		this.role_food = role_food;
	}






	public Integer getRole_wood() {
		return role_wood;
	}






	public void setRole_wood(Integer role_wood) {
		this.role_wood = role_wood;
	}






	public Integer getRole_stone() {
		return role_stone;
	}






	public void setRole_stone(Integer role_stone) {
		this.role_stone = role_stone;
	}






	public String getFaction_name() {
		return faction_name;
	}






	public void setFaction_name(String faction_name) {
		this.faction_name = faction_name;
	}






	public Integer getTrans_cd() {
		return trans_cd;
	}






	public void setTrans_cd(Integer trans_cd) {
		this.trans_cd = trans_cd;
	}






	public Integer getStore() {
		return store;
	}






	public void setStore(Integer store) {
		this.store = store;
	}






	public Integer getReinforce() {
		return reinforce;
	}






	public void setReinforce(Integer reinforce) {
		this.reinforce = reinforce;
	}






	public Integer getLoyal() {
		return loyal;
	}






	public void setLoyal(Integer loyal) {
		this.loyal = loyal;
	}


	public String getName() {
		return name;
	}






	public void setName(String name) {
		this.name = name;
	}






	public Integer getEat() {
		return eat;
	}




	public void setEat(Integer eat) {
		this.eat = eat;
	}




	public Long getCity_id() {
		return city_id;
	}


	public void setCity_id(Long city_id) {
		this.city_id = city_id;
	}


	public Long getRole_id() {
		return role_id;
	}


	public void setRole_id(Long role_id) {
		this.role_id = role_id;
	}


	public Integer getSite_x() {
		return site_x;
	}


	public void setSite_x(Integer site_x) {
		this.site_x = site_x;
	}


	public Integer getSite_y() {
		return site_y;
	}


	public void setSite_y(Integer site_y) {
		this.site_y = site_y;
	}


	public Integer getHome() {
		return home;
	}


	public void setHome(Integer home) {
		this.home = home;
	}


	public Integer getRace() {
		return race;
	}


	public void setRace(Integer race) {
		this.race = race;
	}






    public Integer getYield_food()
    {
        return yield_food;
    }






    public void setYield_food(Integer yield_food)
    {
        this.yield_food = yield_food;
    }






    public Integer getYield_wood()
    {
        return yield_wood;
    }






    public void setYield_wood(Integer yield_wood)
    {
        this.yield_wood = yield_wood;
    }






    public Integer getYield_stone()
    {
        return yield_stone;
    }






    public void setYield_stone(Integer yield_stone)
    {
        this.yield_stone = yield_stone;
    }






    public String getRole_name()
    {
        return role_name;
    }






    public void setRole_name(String role_name)
    {
        this.role_name = role_name;
    }






    public int getRole_lev()
    {
        return role_lev;
    }






    public void setRole_lev(int role_lev)
    {
        this.role_lev = role_lev;
    }






    public String getRole_icon()
    {
        return role_icon;
    }






    public void setRole_icon(String role_icon)
    {
        this.role_icon = role_icon;
    }
	
	
	
}
