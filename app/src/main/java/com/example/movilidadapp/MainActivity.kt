package com.example.movilidadapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.list_layout.view.*


class MainActivity : AppCompatActivity() {

    init {
        startVisitors()
        startWindows()
        startGame()
        selectWinners()

    }

    companion object {
        const val STATE_CLOSE = "C"
        const val STATE_OPEN = "A"
        const val STATE_LEFT = "I"
        const val STATE_RIGHT = "D"
    }

    private lateinit var visitors: BooleanArray
    private lateinit var windows: Array<String>

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        setResults()
        more.setOnClickListener { moreWinners() }
        reset.setOnClickListener { resetAll() }
        start.setOnClickListener { initAll() }

    }

    private fun startVisitors() {
        visitors = BooleanArray(64) { false }
    }

    private fun startWindows() {
        windows = Array<String>(64) { STATE_OPEN }
    }

    private fun startGame() {
        visitors.forEachIndexed { i, b ->
            windows.forEachIndexed { j, s ->
                setStateWindows(i + 1, j + 1, s, windows)
            }
        }
        windows.forEachIndexed { index, s ->
            Log.e("WINDOWS", "index: ${index + 1} result: $s")
        }

    }

    private fun isMultiply(n1: Int, n2: Int): Boolean {
        when {
            n2 % n1 == 0 -> {
                return true
            }
        }
        return false
    }

    private fun isPair(n: Int): Boolean {
        when {
            n % 2 == 0 -> {
                return true
            }
        }
        return false
    }

    private fun openLeft(state: String): String {
        when (state) {
            STATE_RIGHT -> {
                return STATE_OPEN
            }
            STATE_CLOSE -> {
                return STATE_LEFT
            }

        }
        return state
    }

    private fun openRight(state: String): String {
        when (state) {
            STATE_LEFT -> {
                return STATE_OPEN
            }
            STATE_CLOSE -> {
                return STATE_RIGHT
            }
        }
        return state
    }

    private fun closeLeft(state: String): String {
        when (state) {
            STATE_LEFT -> {
                return STATE_CLOSE
            }
            STATE_OPEN -> {
                return STATE_RIGHT
            }

        }
        return state
    }

    private fun closeRight(state: String): String {
        when (state) {
            STATE_RIGHT -> {
                return STATE_CLOSE
            }
            STATE_OPEN -> {
                return STATE_LEFT
            }


        }
        return state
    }

    private fun setStateWindows(n1: Int, n2: Int, state: String, windows: Array<String>) {
        when {
            n1 == 1 -> {
                windows[n2 - 1] = STATE_LEFT
                return
            }
            n1 == 2 -> {
                if (isPair(n2)) {
                    windows[n2 - 1] = openRight(state)
                    return

                }
            }
            n1 == 64 -> {
                windows[n2 - 1] = openRight(state)
                return
            }
            isPair(n1) -> {
                if (isMultiply(n1, n2)) {
                    windows[n2 - 1] = openRight(state)
                    windows[n2 - 1] = closeLeft(state)
                    return
                }
            }
            !isPair(n1) -> {
                if (isMultiply(n1, n2)) {
                    windows[n2 - 1] = openLeft(state)
                    windows[n2 - 1] = closeRight(state)
                    return
                }
            }
        }
    }

    private fun selectWinners(){
        windows.forEachIndexed { i, b ->
            when{
                i==0->{
                    if (windows[windows.size-1] == STATE_CLOSE && windows[i+1] == (STATE_CLOSE) && b == STATE_OPEN){
                        visitors[i]= true
                    }

                }
                i>0 && i<windows.size-1->{
                    if (windows[i-1] == STATE_CLOSE && windows[i+1] == (STATE_CLOSE) && b == STATE_OPEN){
                        visitors[i]= true
                    }
                }
                i==windows.size-1->{
                    if (windows[windows.size-2] == STATE_CLOSE && windows[0] == (STATE_CLOSE) && b == STATE_OPEN){
                        visitors[i]= true
                    }
                }
            }
        }
    }
    private fun moreWinners(){
        windows.forEachIndexed { i, b ->
            if (b == STATE_OPEN) visitors[i]= true
        }
        setResults()
    }

    private fun resetAll(){
        startVisitors()
        startWindows()
        setResults()
    }

    private fun initAll(){
        startVisitors()
        startWindows()
        startGame()
        selectWinners()
        setResults()
    }

    private fun setResults(){
        val adapter = CustomAdapter(visitors, windows)
        recyclerView.adapter = adapter
    }

}
