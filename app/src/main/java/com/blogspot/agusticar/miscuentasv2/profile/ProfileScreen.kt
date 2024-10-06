package com.blogspot.agusticar.miscuentasv2.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blogspot.agusticar.miscuentasv2.R
import com.blogspot.agusticar.miscuentasv2.components.BoardType
import com.blogspot.agusticar.miscuentasv2.components.HeadSetting
import com.blogspot.agusticar.miscuentasv2.components.ModelButton
import com.blogspot.agusticar.miscuentasv2.components.TextFieldComponent
import com.blogspot.agusticar.miscuentasv2.createprofile.CreateProfileViewModel
import com.blogspot.agusticar.miscuentasv2.createprofile.ProfileImageWithCamera


@Composable

fun ProfileScreen(createViewModel: CreateProfileViewModel) {

    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {

        ProfileImageWithCamera(createViewModel, 250)
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            ModelButton(
                text = "Change Photo",
                R.dimen.text_title_small, modifier = Modifier.width(220.dp),
                false,
                onClickButton = {}
            )
        }
            ProfileData(R.string.name, onClickFieldData = {})
            ProfileData(R.string.userName, onClickFieldData = {})
            ProfileData(R.string.password, onClickFieldData = {})
        }
    }


@Composable

private fun ProfileData(title: Int, onClickFieldData: () -> Unit) {
    Column(verticalArrangement = Arrangement.Center) {

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, start = 30.dp),
            text = stringResource(id = title),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    end = 15.dp, start = 15
                        .dp
                ),
            Arrangement.SpaceEvenly,
            Alignment.CenterVertically
        )
        {
            TextFieldComponent(modifier = Modifier.weight(0.60f),
                label = "", inputText = "", onTextChange = {}, type = BoardType.TEXT, true)
            ModelButton(
                text = stringResource(id = R.string.change),
                R.dimen.text_title_small,
                modifier = Modifier.weight(0.40f),
                false,
                onClickButton = onClickFieldData
            )

        }
    }


}
