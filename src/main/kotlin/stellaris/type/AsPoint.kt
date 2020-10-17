package stellaris.type

import mindustry.type.Item
import mindustry.ctype.ContentList
import mindustry.Vars
import mindustry.ctype.ContentType

public class AsPoint() : ContentList {
    
    
    override fun load() {
        
    }
    
    
    
    enum class PointStack(val point:Int = 0, val realname:String ? = null, private val isModlue:Boolean = false) {
        copper(1),
        lead(2),
        metaglass(4),
        graphite(4),
        sand(2),
        coal(3),
        titanium(5),
        thorium(9),
        scrap(3),
        silicon(5),
        plastanium(16),
        phasefabric(25, "phase-fabric"),
        surgealloy(41, "surge-alloy"),
        sporePod(4, "spore-pod"),
        blastCompound(11, "blast-compound"),
        pyratite(7);
        fun get() : Item? = if(isModlue) null else getItem()
        fun getItem() : Item? = Vars.content.getByName(ContentType.item, realname ?: name)
        fun getP() : Int = point
        
        companion object {
            @JvmStatic
            fun getPointByName(name:String) : Int? = valueOf(name).point
            
            @JvmStatic
            fun checkPoint(item:Item, pointd:Int) : Boolean? = if(valueOf(item.toString())?.point >= pointd) true else false
        }
    }
}

