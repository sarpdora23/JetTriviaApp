package com.sinamekidev.jettrivia.component

import android.widget.ProgressBar
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sinamekidev.jettrivia.model.Question
import com.sinamekidev.jettrivia.model.QuestionItem
import com.sinamekidev.jettrivia.screen.QuestionViewModel
import com.sinamekidev.jettrivia.util.getColor
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun TriviaHome(questionViewModel: QuestionViewModel = hiltViewModel()){
    val question = questionViewModel.getQuestion()
    Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFF1C232E)) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(9.dp)){
            SpecialProgressBar(questInd = questionViewModel.questionIndex.value.toFloat(),
                questSize =questionViewModel.questionSize.toFloat() )
            Spacer(modifier = Modifier.height(5.dp))
            DrawDottedLine(pathEffect = PathEffect.dashPathEffect(intervals = floatArrayOf(10f,10f),0f))
            Spacer(modifier = Modifier.height(25.dp))
            AnimatedVisibility(visible =(question != null) ) {
                QuestionScreen(question = question!!) {
                    questionViewModel.nextQuestion()
                }
            }
        }
    }
}

@Composable
fun DrawDottedLine(pathEffect: PathEffect){
    Canvas(modifier = Modifier.fillMaxWidth().height(2.dp)){
        drawLine(Color(0xFF6D6D6D), start = Offset(0f,0f)
        , end = Offset(size.width,0f),pathEffect = pathEffect)
    }
}
@Composable
fun QuestionScreen(question: QuestionItem =
                       QuestionItem("Answer1","Category",
                           listOf("Answer1","Ans2","Ans3"),"What is the question?"),
                   nextQuestion:()->Unit){
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(9.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = question.question, style = MaterialTheme.typography.h5, color = Color(0xFF545464))
        Spacer(modifier = Modifier.height(38.dp))
        LazyColumn{
            items(question.choices){
                var rowSelected = remember {
                    mutableStateOf(false)
                }
                rowSelected.value = false
                AnswerRow(rowSelected,it,question.answer) { ->
                    rowSelected.value = true
                    if (it.equals(question.answer)) {
                        nextQuestion()
                    }

                }
            }
        }
    }
}

@Composable
fun AnswerRow(isSelected: MutableState<Boolean>,answerText:String,realAnswer:String,onSelect:() -> Unit){
    Card(modifier = Modifier
        .clickable { onSelect.invoke() }
        .fillMaxWidth()
        .padding(5.dp).clip(RoundedCornerShape(15.dp))
        .border(
            2.dp, getColor(isSelected.value, answerText, realAnswer),
            RoundedCornerShape(15.dp)
        ), backgroundColor = Color.Transparent) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(selected = isSelected.value, enabled = true,
                onClick = {},
                colors = RadioButtonDefaults.colors(unselectedColor = getColor(isSelected.value,answerText,realAnswer),
                    selectedColor = getColor(isSelected.value,answerText,realAnswer),
                ))
            Spacer(modifier = Modifier.width(15.dp))
            Text(text = answerText, fontSize = 18.sp, color = getColor(isSelected.value,answerText,realAnswer))
        }
    }
}
@Preview(name = "Progress Barr")
@Composable
fun SpecialProgressBar(questInd:Float=1f,questSize:Float=4875f){
    Column() {
        LinearProgressIndicator(color = Color(0xFF396485),
            backgroundColor = Color(0xFF3F4F80),
            progress = questInd / questSize,
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
                .clip(RoundedCornerShape(15.dp)))
        Spacer(modifier = Modifier.height(9.dp))
        Text(text = "${questInd.toInt()} / ${questSize.toInt()}",
            fontSize = 25.sp, fontWeight = FontWeight.Bold,
        color = Color(0xFF727272), modifier = Modifier.padding(start = 15.dp)
        )
    }

}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewTriviaHome(){
    TriviaHome()
}