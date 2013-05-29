package org.nooon.kutya.ui.pages.worksheet;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.util.DefaultQueryModifierDelegate;
import com.vaadin.addon.jpacontainer.util.HibernateLazyLoadingDelegate;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import org.nooon.core.model.entity.Work;
import org.nooon.core.model.entity.Work_;
import org.nooon.core.model.entity.Worksheet;
import org.nooon.core.model.entity.Worksheet_;
import org.nooon.core.model.entity.base.BaseEntity_;
import org.nooon.core.utils.persistence.PredicateBuilder;
import org.nooon.kutya.ui.pages.work.WorkDetailField;
import org.nooon.kutya.ui.template.entity.EntityEditPopup;

import javax.persistence.criteria.*;
import java.util.List;


public class WorksheetEditPopup extends EntityEditPopup<Worksheet> {

    public WorksheetEditPopup(String caption, JPAContainer<Worksheet> container, Object itemID) {
        super(caption, container, itemID);
    }

    @Override
    protected void createBinding(Layout formLayout) {

        binder = new BeanFieldGroup<Worksheet>(Worksheet.class);
        binder.setBuffered(true);
        binder.setItemDataSource(
                String.class.equals(itemID.getClass())
                        ? new BeanItem<Worksheet>(sourceContainer.getItem(itemID).getEntity())
                        : ((BeanItem<Worksheet>) itemID));

        TextField nameField = new TextField(getText("worksheet.name"));
        nameField.setNullRepresentation("");
        formLayout.addComponent(nameField);
        binder.bind(nameField, Worksheet_.name.getName());

        TextArea descriptionField = new TextArea(getText("worksheet.comment"));
        descriptionField.setNullRepresentation("");
        descriptionField.setWidth(100, Unit.PERCENTAGE);
        descriptionField.setHeight(10, Unit.EM);
        formLayout.addComponent(descriptionField);
        binder.bind(descriptionField, Worksheet_.comment.getName());


        JPAContainer<Work> works = new JPAContainer<Work>(Work.class);
        works.setEntityProvider(getEntityProvider(Work.class));
        works.getEntityProvider().setQueryModifierDelegate(new DefaultQueryModifierDelegate() {
            @Override
            public void filtersWillBeAdded(CriteriaBuilder criteriaBuilder, CriteriaQuery<?> query, List<Predicate> predicates) {
                Root from = query.getRoots().iterator().next();
                predicates.add(criteriaBuilder.and(
                        criteriaBuilder.isTrue(from.get(BaseEntity_.active)),
                        criteriaBuilder.equal(from.get(Work_.worksheet), sourceContainer.getItem(itemID).getEntity())
                ));

            }
        });
        works.getEntityProvider().setLazyLoadingDelegate(new HibernateLazyLoadingDelegate());

        WorkDetailField workDetailField = new WorkDetailField(binder.getItemDataSource().getBean(), works, PredicateBuilder.<Work>activeFilter());
        workDetailField.setCaption(getText("worksheet.works"));
        workDetailField.setWidth(100, Unit.PERCENTAGE);
        workDetailField.setHeight(20, Unit.EM);
        formLayout.addComponent(workDetailField);
        binder.bind(workDetailField, Worksheet_.works.getName());


    }
}
