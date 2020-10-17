package stellaris

import arc.Events
import arc.util.Log
import arc.util.Time
import mindustry.Vars
import mindustry.game.EventType
import mindustry.game.EventType.WorldLoadEvent
import mindustry.mod.Mod
import mindustry.ui.dialogs.BaseDialog
import stellaris.content.AsBlocks
public open class TestMod : Mod() {
    init {
        
    }

    override fun loadContent() {
        AsBlocks().load()

    }
}
