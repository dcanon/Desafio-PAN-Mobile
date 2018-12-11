package br.com.bancopan.topgames.main.view

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.bancopan.topgames.R
import br.com.bancopan.topgames.databinding.FragmentGameDetailBinding
import br.com.bancopan.topgames.repository.data.Game
import br.com.bancopan.topgames.utils.Constants.BUNDLE

class GameDetailFragment : DialogFragment() {

    companion object {
        fun newInstance(game: Game): GameDetailFragment {
            val frag = GameDetailFragment()
            val args = Bundle()
            args.putParcelable(BUNDLE, game)
            frag.arguments = args
            return frag
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentGameDetailBinding>(
            inflater, R.layout.fragment_game_detail, container, false
        )
        binding.item = arguments?.getParcelable(BUNDLE)

        return binding.root
    }

}
