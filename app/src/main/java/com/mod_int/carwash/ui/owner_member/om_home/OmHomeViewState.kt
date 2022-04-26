package com.mod_int.carwash.ui.owner_member.om_home

import com.mod_int.carwash.base.ViewState
import com.mod_int.carwash.model.BannerItem

sealed class OmHomeViewState : ViewState {
    object ChangeInfo : OmHomeViewState()
    object RouteOmJoin : OmHomeViewState()
    object RouteWebViewSuggestOm1 : OmHomeViewState()
    object RouteWebViewSuggestOm2 : OmHomeViewState()
    data class GetBannerList(val list : List<BannerItem>) :OmHomeViewState()
}


