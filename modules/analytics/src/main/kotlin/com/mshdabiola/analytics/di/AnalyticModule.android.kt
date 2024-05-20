package com.mshdabiola.analytics.di

import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics
import com.google.firebase.analytics.logEvent
import com.mshdabiola.analytics.AnalyticsEvent
import com.mshdabiola.analytics.AnalyticsHelper
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

actual val analyticsModule: Module
    get() = module {

        single {
            object : AnalyticsHelper {
                val firebaseAnalytics = Firebase.analytics
                override fun logEvent(event: AnalyticsEvent) {
                    firebaseAnalytics.logEvent(event.type) {
                        for (extra in event.extras) {
                            // Truncate parameter keys and values according to firebase maximum length values.
                            param(
                                key = extra.key.take(40),
                                value = extra.value.take(100),
                            )
                        }
                    }
                }
            }
        } bind AnalyticsHelper::class
    }
