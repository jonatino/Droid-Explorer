package com.droid.explorer.command.shell.impl

import com.droid.explorer.command.shell.ShellCommand
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Jonathan on 11/2/2017.
 */
class SetDate(date: Date) : ShellCommand() {

    private val dateFormat = SimpleDateFormat("MMdd0000yyyy.05")

    override val shellArgs = arrayOf("date", dateFormat.format(date))

}