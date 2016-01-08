package com.coq;

/**
 * Created by dabrowski on 27/12/2015.
 */
import com.intellij.ide.structureView.StructureViewModel;
import com.intellij.ide.structureView.StructureViewModelBase;
import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.util.treeView.smartTree.Sorter;
import com.intellij.psi.PsiFile;
import com.coq.psi.CoqFile;
import org.jetbrains.annotations.NotNull;

public class CoqStructureViewModel extends StructureViewModelBase implements
        StructureViewModel.ElementInfoProvider {
    public CoqStructureViewModel(PsiFile psiFile) {
        super(psiFile, new CoqStructureViewElement(psiFile));
    }

    @NotNull
    public Sorter[] getSorters() {
        return new Sorter[] {Sorter.ALPHA_SORTER};
    }


    @Override
    public boolean isAlwaysShowsPlus(StructureViewTreeElement element) {
        return false;
    }

    @Override
    public boolean isAlwaysLeaf(StructureViewTreeElement element) {
        return element instanceof CoqFile;
    }
}
