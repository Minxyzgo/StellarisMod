public open class TsItems : ContentList{
    val ts:Item ? = null
    
    override fun load(){
        ts ?: newItem("ss")
    }
    
    
    class newItem constructor(name:String) : Item(name){
        init {
            this.color = Color.red
            this.cost = 2
        }
    }
}
