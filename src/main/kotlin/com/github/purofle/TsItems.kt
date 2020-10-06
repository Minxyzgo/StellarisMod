package com.github.purofle
import mindustry.ctype.ContentList
import arc.graphics.Color
import mindustry.type.Item

public open class TsItems : ContentList{
    var ts:Item ? = null
    
    override fun load(){
        ts ?: newItem("ss")
    }
    
    
    class newItem constructor(name:String) : Item(name){
        init {
            this.color = Color.red
            this.cost = 2.0f
        }
    }
}
