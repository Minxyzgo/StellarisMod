package stellaris.content

import arc.func.Boolf
import arc.func.Boolp
import arc.func.Prov
import arc.scene.ui.layout.Table
import arc.struct.ObjectMap
import arc.util.io.Reads
import arc.util.io.Writes
import mindustry.Vars
import mindustry.Vars.content
import mindustry.content.Items
import mindustry.ctype.ContentList
import mindustry.ctype.ContentType
import mindustry.gen.Building
import mindustry.type.Category
import mindustry.type.Item
import mindustry.type.ItemStack
import mindustry.ui.Cicon
import mindustry.ui.ItemImage
import mindustry.ui.MultiReqImage
import mindustry.ui.ReqImage
import mindustry.world.blocks.production.GenericSmelter
import mindustry.world.consumers.Consume
import mindustry.world.consumers.ConsumeItemFilter
import mindustry.world.consumers.ConsumeItems
import stellaris.type.AsPoint


public class AsBlocks : ContentList {
    val MatterEnergyTransformator = object : GenericSmelter("matter-energy transformator") {
        init {
            outputItem = ItemStack(Items.lead, 1)
            requirements(Category.crafting, ItemStack.with())
            itemCapacity = 1000
            var ilist = ObjectMap<Item, ItemStack>()
            for (item in AsPoint.PointStack.values()) {
                ilist.put(item.get(), ItemStack(item.get(), 150 / item.getP()))
            }

            consumes.add(object : ConsumeItemFilter(Boolf { i: Item -> ilist.containsKey(i) }) {
                override fun build(tile: Building?, table: Table) {
                    val image = MultiReqImage()
                    content.items().each({ i: Item -> filter[i] && i.unlockedNow() }) { item: Item ->
                        image.add(ReqImage(ItemImage(item.icon(Cicon.medium)),
                                Boolp { tile != null && !(tile.items.empty()) && (tile as EnergyBuild).getItem() === item }))
                    }
                    table.add(image).size(8f * 4f)
                }

                override fun valid(entity: Building): Boolean {
                    return !entity.items.empty()
                }
            })

//            consumes.items(*ilist.toTypedArray())
            
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
             
             
             
             override fun acceptItem(source:Building, item:Item) : Boolean {
                 if(super.acceptItem(source, item) && (((itemId?.toShort()) == item.id) || items.empty())) {
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
