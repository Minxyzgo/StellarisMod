package stellaris.content
import mindustry.ctype.ContentList
import mindustry.world.blocks.production.GenericSmelter
import mindustry.ctype.ContentType
import mindustry.gen.Building
import mindustry.type.ItemStack
import arc.util.io.Reads
import arc.util.io.Writes
import mindustry.Vars
import mindustry.world.consumers.ConsumeItems
import mindustry.world.consumers.Consume
import mindustry.content.Items
import mindustry.type.Category
import mindustry.type.Item
import stellaris.type.AsPoint
import arc.func.Prov

public class AsBlocks : ContentList {
    val MatterEnergyTransformator = object : GenericSmelter("matter-energy transformator") {
        init {
            outputItem = ItemStack(Items.lead, 1)
            requirements(Category.crafting, ItemStack.with())
            var ilist = arrayListOf<ItemStack>()
            for (item in AsPoint.PointStack.values()) {
                ilist.add(ItemStack(item.get(), 150 / item.getP()))
            }
            consumes.items(*ilist.toTypedArray())
            
            buildType = Prov {
                EnergyBuild()
            }
        }
        
        inner class EnergyBuild : GenericSmelter.SmelterBuild() {
            private var itemId:Int ? = null
            
             override fun consValid():Boolean {
                 for (item in AsPoint.PointStack.values()) {
                     if(!items.has(item.get())) continue else return true
                 }
                 
                 return super.consValid()
             }
             
             override fun readBase(read:Reads) {
                 super.readBase(read)
                 val id:Int = read.i()
                 if(id != -1) itemId = id
             }
             
             override fun writeBase(write:Writes) {
                 super.writeBase(write)
                 write.i(itemId ?: -1)
             }
             
             override fun consume() {
                 for(cons:Consume in block.consumes.all()) {
                     (cons as? ConsumeItems)?.trigger(this)
                 }
             }
             
             override fun acceptItem(source:Building, item:Item) : Boolean {
                 if(super.acceptItem(source, item) && ((itemId?.toShort()) == item.id)) {
                     itemId = item.id.toInt()
                     return true
                 } else {
                     return false
                 }
             }
             
             fun getItem(): Item? = Vars.content.getByID(ContentType.item, itemId ?: -1)
             
        }
    }
    
    
    override fun load() {
        
    }
}
