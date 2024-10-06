package com.blogspot.agusticar.miscuentasv2.profile

import android.util.Log
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.blogspot.agusticar.miscuentasv2.R
import com.blogspot.agusticar.miscuentasv2.components.BoardType
import com.blogspot.agusticar.miscuentasv2.components.ModelButton
import com.blogspot.agusticar.miscuentasv2.components.TextFieldComponent
import com.blogspot.agusticar.miscuentasv2.createprofile.CreateProfileViewModel
import com.blogspot.agusticar.miscuentasv2.createprofile.ProfileImageWithCamera
import kotlinx.coroutines.launch


@Composable

fun ProfileScreen(createViewModel: CreateProfileViewModel) {

    val name by createViewModel.name.observeAsState("")
    val userName by createViewModel.username.observeAsState("")
    val password by createViewModel.password.observeAsState("")
    val selectedImageUri by createViewModel.selectedImageUri.observeAsState(null)
    val enableChangeImageButton by createViewModel.enableChangeImage.observeAsState(false)
    val enableNameButton by createViewModel.enableNameButton.observeAsState(false)
    //val enableUserNameButton by createViewModel.enableChangeImage.observeAsState(false)
    //val enablePasswordButton by createViewModel.enableChangeImage.observeAsState(false)

    val scope = rememberCoroutineScope()
    //createViewModel.onImageNoSelected()
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
    Log.d("imageUri",selectedImageUri.toString ())


        ProfileImageWithCamera(createViewModel)
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            ModelButton(
                text = "Change Photo",
                R.dimen.text_title_small, modifier = Modifier.width(220.dp),
                enableChangeImageButton,
                onClickButton = {

                    scope.launch {
                        try {
                            selectedImageUri?.let { createViewModel.saveImageUri(it)
                            Log.d("ImageUri","imagen guardada ")}
                        } catch (e:Exception){
                            Log.d("ImageUri","imagen  No guardada ")
                        }
                        }
                }
            )
        }
        ProfileData(R.string.name,
            BoardType.TEXT,
            R.string.newName,
            name,
            invisible =  false ,
            enableButton = enableNameButton,
            onClickFieldData = {createViewModel.onNameChanged(name)})
        ProfileData(
            R.string.userName,
            BoardType.TEXT,
            R.string.newUserName,
            userName,
            invisible =  false ,
            enableButton = false,
            onClickFieldData = {})
        ProfileData(
            R.string.password,
            BoardType.PASSWORD,
            R.string.newPassword,
            password,
            invisible =  true ,
            enableButton = false,
            onClickFieldData = {})
    }
}


@Composable

private fun ProfileData(
    title: Int,
    type: BoardType,
    label: Int,
    oldInput: String,
    invisible:Boolean,
    enableButton:Boolean,
    onClickFieldData: () -> Unit
) {
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
                label = stringResource(id = label),
                inputText = oldInput,
                onTextChange ={onClickFieldData()} ,
                type,
                invisible
            )
            ModelButton(
                text = stringResource(id = R.string.change),
                R.dimen.text_title_small,
                modifier = Modifier.weight(0.40f),
                enableButton,
                onClickButton = onClickFieldData
            )

        }
    }


}
