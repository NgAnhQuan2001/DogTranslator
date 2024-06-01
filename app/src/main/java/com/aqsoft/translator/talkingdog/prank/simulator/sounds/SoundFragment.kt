package com.aqsoft.translator.talkingdog.prank.simulator.sounds

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.aqsoft.translator.talkingdog.prank.simulator.R
import com.aqsoft.translator.talkingdog.prank.simulator.databinding.FragmentSoundsBinding
import com.aqsoft.translator.talkingdog.prank.simulator.training.TrainingModel
import com.google.gson.Gson


class SoundFragment : Fragment() {

    private val mHelpGuid: ArrayList<TrainingModel> = ArrayList()
    private var binding: FragmentSoundsBinding? = null
    private var soundAdapter: SoundAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSoundsBinding.inflate(layoutInflater)
        return binding?.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        handleInteract()

    }

    private fun initData() {

        initialize()
        binding!!.rclView.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(requireContext(), 3)
            soundAdapter = SoundAdapter(
                mHelpGuid, context
            ) { pos, trainingModel ->
                val intent = Intent(context, SoundItemActivity::class.java)
                intent.putExtra("TYPE", pos)
                intent.putExtra("OBJECT", Gson().toJson(trainingModel))
                startActivity(intent)
            }
            adapter = soundAdapter
        }
    }

    private fun handleInteract() {
    }

    override fun onPause() {
        super.onPause()
    }

    private fun initialize() {
        mHelpGuid.add(TrainingModel(R.drawable.ic_sound_hi, "Chào", R.raw.dog_hi))
        mHelpGuid.add(TrainingModel(R.drawable.ic_sound_wonder, "Tuyệt vời", R.raw.dog_wonder))
        mHelpGuid.add(TrainingModel(R.drawable.ic_sound_happy, "Hạnh phúc", R.raw.dog_happy))
        mHelpGuid.add(TrainingModel(R.drawable.ic_sound_love, "Yêu", R.raw.dog_love))
        mHelpGuid.add(TrainingModel(R.drawable.ic_sound_dance, "Nhảy múa", R.raw.dog_dance))
        mHelpGuid.add(TrainingModel(R.drawable.ic_sound_agree, "Đồng ý", R.raw.dog_agree))
        mHelpGuid.add(TrainingModel(R.drawable.ic_sound_hand_clap, "Vỗ tay", R.raw.dog_handclap))
        mHelpGuid.add(TrainingModel(R.drawable.ic_sound_cry, "Khóc", R.raw.dog_cry))
        mHelpGuid.add(TrainingModel(R.drawable.ic_sound_cry_lying, "Khóc nằm", R.raw.dog_crying))
        mHelpGuid.add(TrainingModel(R.drawable.ic_sound_no, "Không!", R.raw.dog_no))
        mHelpGuid.add(TrainingModel(R.drawable.ic_sound_yes, "Có", R.raw.dog_yeah))
        mHelpGuid.add(TrainingModel(R.drawable.ic_sound_wow, "Wow", R.raw.dog_wow))
        mHelpGuid.add(TrainingModel(R.drawable.ic_sound_pet, "Thú cưng", R.raw.dog_pet))
        mHelpGuid.add(TrainingModel(R.drawable.ic_sound_begging, "Van xin", R.raw.dog_begging))
        mHelpGuid.add(TrainingModel(R.drawable.ic_sound_want_sleep, "Muốn ngủ", R.raw.dog_want_sleep))
        mHelpGuid.add(TrainingModel(R.drawable.ic_sound_yeah, "Vâng", R.raw.dog_yeah))
        mHelpGuid.add(TrainingModel(R.drawable.ic_sound_lie, "Nói dối", R.raw.dog_lie))
        mHelpGuid.add(TrainingModel(R.drawable.ic_sound_startle, "Sợ hãi", R.raw.dog_startle))
        mHelpGuid.add(TrainingModel(R.drawable.ic_sound_scratch, "Gãi", R.raw.dog_scrath))
        mHelpGuid.add(TrainingModel(R.drawable.ic_sound_sick, "Ốm", R.raw.dog_scared))
        mHelpGuid.add(TrainingModel(R.drawable.ic_sound_exhausted, "Kiệt sức", R.raw.dog_exhausted))
        mHelpGuid.add(TrainingModel(R.drawable.ic_sound_scare, "Sợ", R.raw.dog_scared))
        mHelpGuid.add(TrainingModel(R.drawable.ic_sound_argry, "Tức giận", R.raw.dog_angry))
        mHelpGuid.add(TrainingModel(R.drawable.ic_sound_super_argry, "Siêu tức giận", R.raw.dog_super_angry))
        mHelpGuid.add(TrainingModel(R.drawable.ic_sound_sick, "Ốm", R.raw.dog_sick))
        mHelpGuid.add(TrainingModel(R.drawable.ic_sound_sad, "Buồn", R.raw.dog_sad))

    }

}