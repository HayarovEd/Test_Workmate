package com.edurda77.test.workmate.ui.list_characters

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edurda77.test.workmate.R
import com.edurda77.test.workmate.domain.model.Gender
import com.edurda77.test.workmate.domain.model.StatusCharacter

@Composable
fun BottomSheetContent(
    modifier: Modifier = Modifier,
    status: StatusCharacter?,
    species: String,
    type: String,
    gender: Gender?,
    onUpdateStatus: (StatusCharacter) -> Unit,
    onUpdateSpecies: (String) -> Unit,
    onUpdateType: (String) -> Unit,
    onUpdateGender: (Gender) -> Unit,
    onClickCansel: () -> Unit,
    onClickConfirm: () -> Unit,
    onClickResetFilters: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Text(
            modifier = modifier
                .fillMaxWidth(),
            text = stringResource(R.string.select_filters),
            color = Color.Black,
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = modifier.height(10.dp))
        Row(
            modifier = modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            StatusCharacter.entries.forEach {
                Row(
                    modifier = modifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Color.Green,
                            unselectedColor = Color.Black
                        ),
                        selected = it == status,
                        onClick = {
                            onUpdateStatus(it)
                        }
                    )
                    Text(
                        modifier = modifier,
                        text = stringResource(it.resId),
                        color = if (it == status) Color.Green else Color.Black,
                        fontSize = 10.sp,
                    )
                }
            }
        }
        Spacer(modifier = modifier.height(10.dp))
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth(),
            value = species,
            onValueChange = {
                onUpdateSpecies(it)
            },
            label = {
                Text(
                    modifier = modifier,
                    text = stringResource(R.string.enter_species),
                    fontSize = 12.sp,
                )
            }
        )
        Spacer(modifier = modifier.height(10.dp))
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth(),
            value = type,
            onValueChange = {
                onUpdateType(it)
            },
            label = {
                Text(
                    modifier = modifier,
                    text = stringResource(R.string.enter_type),
                    fontSize = 12.sp,
                )
            }
        )
        Spacer(modifier = modifier.height(10.dp))
        Row(
            modifier = modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Gender.entries.forEach {
                Row(
                    modifier = modifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Color.Green,
                            unselectedColor = Color.Black
                        ),
                        selected = it == gender,
                        onClick = {
                            onUpdateGender(it)
                        }
                    )
                    Text(
                        modifier = modifier,
                        text = stringResource(it.resId),
                        color = if (it == gender) Color.Green else Color.Black,
                        fontSize = 10.sp,
                    )
                }
            }
        }
        Spacer(modifier = modifier.height(10.dp))
        TextButton(
            onClick = onClickResetFilters
        ) {
            Text(
                modifier = modifier,
                text = stringResource(R.string.reset_filters),
                color = Color.Red,
                fontSize = 16.sp,
            )
        }
        Spacer(modifier = modifier.height(10.dp))
        Row(
            modifier = modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(
                modifier = modifier.weight(1f),
                onClick = onClickCansel
            ) {
                Text(
                    modifier = modifier,
                    text = stringResource(R.string.consel),
                    color = Color.Black,
                    fontSize = 16.sp,
                )
            }
            Button(
                modifier = modifier.weight(1f),
                /*colors = ButtonDefaults.buttonColors(
                    containerColor =
                ),*/
                onClick = onClickConfirm
            ) {
                Text(
                    modifier = modifier,
                    text = stringResource(R.string.confirm),
                    color = Color.Black,
                    fontSize = 16.sp,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BottomSheetContentView() {
    BottomSheetContent(
        status = StatusCharacter.ALIVE,
        species = "Human",
        type = "Human with baby legs",
        gender = Gender.GENDERLESS,
        onUpdateType = {},
        onClickCansel = {},
        onClickConfirm = {},
        onUpdateGender = {},
        onUpdateStatus = {},
        onUpdateSpecies = {},
        onClickResetFilters = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun BottomSheetContentView2() {
    BottomSheetContent(
        status = StatusCharacter.ALIVE,
        species = "",
        type = "",
        gender = Gender.GENDERLESS,
        onUpdateType = {},
        onClickCansel = {},
        onClickConfirm = {},
        onUpdateGender = {},
        onUpdateStatus = {},
        onUpdateSpecies = {},
        onClickResetFilters = {}
    )
}