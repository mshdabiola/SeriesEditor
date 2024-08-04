package com.mshdabiola.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp
import com.mshdabiola.designsystem.component.SeriesEditorButton
import com.mshdabiola.designsystem.component.SeriesEditorTextField
import com.mshdabiola.generalmodel.Type
import com.mshdabiola.model.ImageUtil
import com.mshdabiola.retex.Latex
import com.mshdabiola.ui.image.DragAndDropImage
import com.mshdabiola.ui.state.ItemUiState
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun QuestionDialog(
    modifier: Modifier = Modifier,
    itemUiState: ItemUiState?,
    examId: Long,
    onDismiss: () -> Unit = {},
) {
    if (itemUiState != null) {
        when (itemUiState.type) {
            Type.EQUATION -> {
                EquationDialog(textFieldState = itemUiState.content, onDismiss = onDismiss)
            }

            Type.TEXT -> {}
            Type.IMAGE -> {
                ImageDialog(
                    textFieldState = itemUiState.content,
                    examId = examId,
                    onDismiss = onDismiss,
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageDialog(
    modifier: Modifier = Modifier,
    textFieldState: TextFieldState,
    examId: Long,
    onDismiss: () -> Unit = {},
) {
    val coroutineScope = rememberCoroutineScope()
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        dismissButton = {
            if (textFieldState.text.isNotBlank()) {
                TextButton(onClick = { textFieldState.clearText() }) {
                    Text("Remove Image")
                }
            }
        },
        confirmButton = {
            SeriesEditorButton(
                onClick = onDismiss,
            ) {
                Text(if (textFieldState.text.isNotBlank()) "Close " else "Add Image")
            }
        },
        text = {
            Box(modifier.aspectRatio(16f / 9f), contentAlignment = Alignment.Center) {
                DragAndDropImage(
                    modifier = modifier.fillMaxSize(),
                    path = ImageUtil.getAppPath(textFieldState.text.toString()).path,
                    isEmpty = textFieldState.text.isBlank(),
// ,
                    onPathChange = { path ->
                        coroutineScope.launch {
                            val name = ImageUtil
                                .saveImage(
                                    textFieldState.text.toString(), // item.content,
                                    path, // text,
                                    examId, // examId,
                                )
                            textFieldState.clearText()

                            textFieldState.edit {
                                append("$examId/$name")
                            }
                        }
                    },
                )
            }
        },
    )
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalLayoutApi::class)
@Composable
fun EquationDialog(
    modifier: Modifier = Modifier,
    textFieldState: TextFieldState,
    onDismiss: () -> Unit = {},
) {
    val focusRequester = remember {
        FocusRequester()
    }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
    val pagerState = rememberPagerState(initialPage = 0) { map.keys.size }
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        dismissButton = {
            if (textFieldState.text.isNotBlank()) {
                TextButton(onClick = { textFieldState.clearText() }) {
                    Text("Remove Equation")
                }
            }
        },
        confirmButton = {
            SeriesEditorButton(
                onClick = onDismiss,
            ) {
                Text(if (textFieldState.text.isNotBlank()) "Close " else "Add Equation")
            }
        },
        text = {
            Column(modifier) {
                ScrollableTabRow(selectedTabIndex = pagerState.currentPage) {
                    map.keys.forEachIndexed { index, s ->

                        Tab(
                            selected = pagerState.currentPage == index,
                            onClick = {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            },
                            text = { Text(s) },
                        )
                    }
                }
                HorizontalPager(state = pagerState, userScrollEnabled = false) {
                    FlowRow(
                        Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .padding(vertical = 4.dp)
                            .verticalScroll(state = scrollState),
                        //  verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        map[map.keys.toList()[it]]!!.forEach {
                            SuggestionChip(
                                onClick = {
                                    textFieldState.edit {
                                        append(it)
                                    }
                                    focusRequester.requestFocus()
                                },
                                label = { Latex(text = it) },
                            )
                        }
                    }
                }
                SeriesEditorTextField(
                    modifier = Modifier
                        .focusRequester(focusRequester)
                        .fillMaxWidth(),
                    label = "Equations",
                    maxNum = TextFieldLineLimits.SingleLine,
                    state = textFieldState,
                )

                key(textFieldState.text) {
                    Latex(
                        modifier = Modifier.fillMaxWidth().height(80.dp),
                        text = textFieldState.text.toString(),
                    )
                }
            }
        },
    )
}

val map = mapOf(
    "Binary" to listOf(
        "\\ast",
        "\\star",
        "\\cdot",
        "\\circ",
        "\\bullet",
        "\\bigcirc",
        "\\diamond",
        "\\times",
        "\\div",
        "\\centerdot",
        "\\circledast",
        "\\circledcirc",
        "\\circleddash",
        "\\dotplus",
        "\\divideontimes",
        "\\pm",
        "\\mp",
        "\\amalg",
        "\\odot",
        "\\ominus",
        "\\oplus",
        "\\oslash",
        "\\otimes",
        "\\wr",
        "\\Box",
        "\\boxplus",
        "\\boxminus",
        "\\boxtimes",
        "\\boxdot",
        "\\square",
        "\\cap",
        "\\cup",
        "\\uplus",
        "\\sqcap",
        "\\sqcup",
        "\\wedge",
        "\\vee",
        "\\dagger",
        "\\ddagger",
        "\\barwedge",
        "\\curlywedge",
        "\\Cap",
        "\\bot",
        "\\intercal",
        "\\doublebarwedge",
        "\\lhd",
        "\\rhd",
        "\\triangleleft",
        "\\triangleright",
        "\\unlhd",
        "\\unrhd",
        "\\bigtriangledown",
        "\\bigtriangleup",
        "\\setminus",
        "\\veebar",
        "\\curlyvee",
        "\\Cup",
        "\\top",
        "\\rightthreetimes",
        "\\leftthreetimes",
    ),
    "Equation" to listOf(
        "\\frac{0}{0}",
        "^0/_0",
        "0_0",
        "0^0",
        "\\sqrt{6}",
        "\\sqrt[2]{0}",
        "\\sum_{k=1}^n k^2",
        "\\prod_{x=4}^x",
        "\\int_a^b f(x)\\,dx.",
        "\\left.\\frac{dx}{dy}\\right|_x",
        "\\frac{\\partial u}{\\partial t}",
    ),
    "Functions" to listOf(
        "\\arccos(x)",
        "\\cos(x)",
        "\\csc(x)",
        "\\exp(x)",
        "\\ker(x)",
        "\\limsup(x)",
        "\\min(x)",
        "\\sinh(x)",
        "\\arcsin(x)",
        "\\cosh(x)",
        "\\deg(x)",
        "\\gcd(x)",
        "\\lg(x)",
        "\\in(x)",
        "\\Pr(x)",
        "\\arctan(x)",
        "\\cot(x)",
        "\\det(x)",
        "\\hom(x)",
        "\\lim_{x \\to \\infty}(x)",
        "\\log(x)",
        "\\sec(x)",
        "\\tan(x)",
        "\\arg(x)",
        "\\coth(x)",
        "\\dim(x)",
        "\\inf(x)",
        "\\liminf(x)",
        "\\max(x)",
        "\\sin(x)",
        "\\tanh(x)",
    ),
    "Delimiter" to listOf(

        "|",
        "\\vert",
        "\\|",
        "\\Vert",
        "\\{",
        "\\}",
        "\\langle",
        "\\rangle",
        "\\lfloor",
        "\\rfloor",
        "\\lceil",
        "\\rceil",
        "/",
        "\\backslash",
        "[",
        "]",
        "\\Uparrow",
        "\\uparrow",
        "\\Downarrow",
        "\\downarrow",
        "\\llcorner",
        "\\lrcorner",
        "\\ulcorner",
        "\\urcorner",
    ),
    "array" to listOf(
        "\\mbox{~and~} ",
        "\\left[ \\begin{array}{cc|r} 3 & 4 & 5 \\\\ 1 & 3 & 729 \\end{array} \\right]",
        "\\begin{eqnarray}\n" +
            "\\cos 2\\theta & = & \\cos^2 \\theta - \\sin^2 \\theta \\\\\n" +
            "& = & 2 \\cos^2 \\theta - 1.\n" +
            "\\end{eqnarray}",
        "\\begin{tabular}{|l|l|l|}\\hline\n" +
            "Chicago&U.S.A.&1893\\\\\n" +
            "Z\\\"{u}rich&Switzerland&1897\\\\\n" +
            "Paris&France&1900\\\\\n" +
            "Heidelberg&Germany&1904\\\\\n" +
            "Rome&Italy&1908\\hline\\end{tabular}",
    ),
    "Miscellaneous" to listOf(
        "\\aleph",
        "\\angle",
        "\\backprime",
        "\\backslash",
        "\\Bbbk",
        "\\bigstar",
        "\\blacklozenge",
        "\\blacksquare",
        "\\blacktriangle",
        "\\blacktriangledown",
        "\\bot",
        "\\cdots",
        "\\circledS",
        "\\clubsuit",
        "\\complement",
        "\\ddots",
        "\\diagdown",
        "\\diagup",
        "\\diamondsuit",
        "\\ell",
        "\\emptyset",
        "\\eth",
        "\\exists",
        "\\Finv",
        "\\flat",
        "\\forall",
        "\\Game",
        "\\hbar",
        "\\heartsuit",
        "\\hslash",
        "\\Im",
        "\\imath",
        "\\infty",
        "\\jmath",
        "\\ldots",
        "\\lozenge",
        "\\measuredangle",
        "\\mho",
        "\\nabla",
        "\\natural",
        "\\neg",
        "\\nexists",
        "\\partial",
        "\\prime",
        "\\Re",
        "\\sharp",
        "\\spadesuit",
        "\\sphericalangle",
        "\\square",
        "\\top",
        "\\triangle",
        "\\triangledown",
        "\\varnothing",
        "\\vartriangle",
        "\\vdots",
        "\\wp",
        "\\|",

    ),
    "Variable Symbol" to listOf(
        "\\sum_0",
        "\\bigcap_0",
        "\\bigodot_0",
        "\\prod_0",
        "\\bigcup_0",
        "\\bigotimes_0",
        "\\coprod_0",
        "\\bigsqcup_0",
        "\\bigoplus_0",
        "\\int_0",
        "\\bigvee_0",
        "\\biguplus_0",
        "\\oint_0",
        "\\oint\\limits_0^y",
        "\\bigwedge_0",
        "\\leqslant_0",
        "\\geqslant_0",
    ),
    "Relation 1" to listOf(
        // eqution
        "\\equiv",
        "\\cong",
        "\\neq",
        "\\sim",
        "\\simeq",
        "\\approx",
        "\\asymp",
        "\\doteq",
        "\\propto",
        "\\models",
        "\\leq",
        "\\prec",
        "\\preceq",
        "\\ll",
        "\\subset",
        "\\subseteq",
        "\\sqsubset",
        "\\sqsubseteq",
        "\\dashv",
        "\\in",
        "\\geq",
        "\\succ",
        "\\succeq",
        "\\gg",
        "\\supset",
        "\\supseteq",
        "\\sqsupset",
        "\\sqsupseteq",
        "\\vdash",
        "\\ni",
        "\\perp",
        "\\mid",
        "\\parallel",
        "\\bowtie",
        "\\Join",
        "\\ltimes",
        "\\rtimes",
        "\\smile",
        "\\frown",
        "\\notin",
    ),
    "Relation 2" to listOf(
        // equal
        "\\approxeq",
        "\\thicksim",
        "\\backsim",
        "\\backsimeq",
        "\\triangleq",
        "\\circeq",
        "\\bumpeq",
        "\\Bumpeq",
        "\\doteqdot",
        "\\thickapprox",
        "\\fallingdotseq",
        "\\risingdotseq",
        "\\varpropto",
        "\\therefore",
        "\\because",
        "\\eqcirc",
        "\\neq",
        "\\leqq",
        "\\leqslant",
        "\\lessapprox",
        "\\lll",
        "\\lessdot",
        "\\lesssim",
        "\\eqslantless",
        "\\precsim",
        "\\precapprox",
        "\\Subset",
        "\\subseteqq",
        "\\sqsubset",
        "\\preccurlyeq",
        "\\curlyeqprec",
        "\\blacktriangleleft",
        "\\trianglelefteq",
        "\\vartriangleleft",
        "\\geqq",
        "\\geqslant",
        "\\gtrapprox",
        "\\ggg",
        "\\gtrdot",
        "\\gtrsim",
        "\\eqslantgtr",
        "\\succsim",
        "\\succapprox",
        "\\Supset",
        "\\supseteqq",
        "\\sqsupset",
        "\\succcurlyeq",
        "\\curlyeqsucc",
        "\\blacktriangleright",
        "\\trianglerighteq",
        "\\vartriangleright",
        "\\lessgtr",
        "\\lesseqgtr",
        "\\lesseqqgtr",
        "\\gtreqqless",
        "\\gtreqless",
        "\\gtrless",
        "\\backepsilon",
        "\\between",
        "\\pitchfork",
        "\\shortmid",
        "\\smallfrown",
        "\\smallsmile",
        "\\Vdash",
        "\\vDash",
        "\\Vvdash",
        "\\shortparallel",
        "\\nshortparallel",
    ),
    "Relation 3" to listOf(

        "\\ncong",
        "\\nmid",
        "\\nparallel",
        "\\nshortmid",
        "\\nshortparallel",
        "\\nsim",
        "\\nVDash",
        "\\nvDash",
        "\\nvdash",
        "\\ntriangleleft",
        "\\ntrianglelefteq",
        "\\ntriangleright",
        "\\ntrianglerighteq",
        "\\nleq",
        "\\nleqq",
        "\\nleqslant",
        "\\nless",
        "\\nprec",
        "\\npreceq",
        "\\precnapprox",
        "\\precnsim",
        "\\lnapprox",
        "\\lneq",
        "\\lneqq",
        "\\lnsim",
        "\\lvertneqq",
        "\\ngeq",
        "\\ngeqq",
        "\\ngeqslant",
        "\\ngtr",
        "\\nsucc",
        "\\nsucceq",
        "\\succnapprox",
        "\\succnsim",
        "\\gnapprox",
        "\\gneq",
        "\\gneqq",
        "\\gnsim",
        "\\gvertneqq",
        "\\nsubseteq",
        "\\nsupseteq",
        "\\nsubseteqq",
        "\\nsupseteqq",
        "\\subsetneq",
        "\\supsetneq",
        "\\subsetneqq",
        "\\supsetneqq",
        "\\varsubsetneq",
        "\\varsupsetneq",
        "\\varsubsetneqq",
        "\\varsupsetneqq",
    ),
    "Arrow 1" to listOf(
        "\\leftarrow", "\\longleftarrow", "\\uparrow",
        "\\Leftarrow", "\\Longleftarrow", "\\Uparrow",
        "\\rightarrow", "\\longrightarrow", "\\downarrow",
        "\\Rightarrow", "\\Longrightarrow", "\\Downarrow",
        "\\leftrightarrow", "\\longleftrightarrow", "\\updownarrow",
        "\\Leftrightarrow", "\\Longleftrightarrow", "\\Updownarrow",
    ),
    "Arrow 2" to listOf(
        "\\mapsto", "\\longmapsto", "\\nearrow",
        "\\hookleftarrow", "\\hookrightarrow", "\\searrow",
        "\\leftharpoonup", "\\rightharpoonup", "\\swarrow",
        "\\leftharpoondown", "\\rightharpoondown", "\\nwarrow",
        "\\rightleftharpoons", "\\leadsto",
    ),
    "Arrow 3" to listOf(
        "\\leftleftarrows",
        "\\leftrightarrows", "\\Lleftarrow", "\\twoheadleftarrow",
        "\\leftarrowtail", "\\looparrowleft", "\\leftrightharpoons",
        "\\curvearrowleft", "\\circlearrowleft", "\\Lsh",
        "\\upuparrows", "\\upharpoonleft", "\\downharpoonleft",
        "\\multimap", "\\leftrightsquigarrow", "\\rightrightarrows",
        "\\rightleftarrows", "\\rightrightarrows", "\\rightleftarrows",
        "\\twoheadrightarrow", "\\rightarrowtail", "\\looparrowright",
        "\\rightleftharpoons", "\\curvearrowright", "\\circlearrowright",
        "\\Rsh", "\\downdownarrows", "\\upharpoonright",
    ),
    "Arrow 4" to listOf(
        "\\nleftarrow",
        "\\nrightarrow",
        "\\nLeftarrow",
        "\\nRightarrow",
        "\\nleftrightarrow",
        "\\nLeftrightarrow",
    ),
    "Acute" to listOf(
        "\\acute{a}",
        "\\breve{a}",
        "\\ddot{a}",
        "\\grave{a}",
        "\\tilde{a}",
        "\\bar{a}",
        "\\check{a}",
        "\\dot{a}",
        "\\hat{a}",
        "\\vec{a}",

    ),
    "Font" to listOf(
        " \\mathcal{A}",
        " \\mathbb{A}",
        " \\mathfrak{A}",
        " \\mathsf{A}",
        " \\mathbf{A}",
        "\\text{ a text }",
    ),
    "Greek" to listOf(
        "\\alpha", "\\beta", "\\gamma", "\\delta", "\\epsilon",
        "\\zeta", "\\eta", "\\theta", "\\iota", "\\kappa", "\\lambda",
        "\\mu", "\\nu", "\\xi", "\\psi", "\\pi", "\\rho", "\\sigma", "\\tau",
        "\\upsilon", "\\phi", "\\omega",
        "\\varepsilon", "\\vartheta", "\\varpi", "\\varrho", "\\varsigma", "\\varphi",
        "\\Gamma", "\\Delta", "\\Epsilon",
        "\\Theta", "\\Lambda", "\\Xi", "\\Psi", "\\Pi", "\\Sigma",
        "\\Upsilon", "\\Phi", "\\Omega",
    ),
    "Overhead" to listOf(
        "\\overline{abc}",
        "\\underline{abc}",
        "\\widehat{abc}",
        "\\widetilde{abc}",
        "\\overrightarrow{abc}",
        "\\overleftarrow{abc}",
        "\\overbrace{abc}",
        "\\underbrace{abc}",
    ),
)
