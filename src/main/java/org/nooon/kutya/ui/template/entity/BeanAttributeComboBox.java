package org.nooon.kutya.ui.template.entity;

import com.vaadin.data.util.BeanItemContainer;
import org.nooon.core.model.entity.base.BaseEntity;
import org.nooon.core.utils.persistence.PredicateBuilder;
import org.nooon.kutya.ui.template.CdiComboBox;

import javax.persistence.metamodel.Attribute;

public class BeanAttributeComboBox extends CdiComboBox {

    private Attribute attribute;

    public BeanAttributeComboBox(String caption, Attribute attribute) {
        super(caption);
        this.attribute = attribute;
    }

    @Override
    public void attach() {
        super.attach();
        setContainerDataSource(new BeanItemContainer(attribute.getJavaType(),
                getBeanHelper().getBeanAttribute(PredicateBuilder.activeFilter(), attribute, BaseEntity.class)));
    }
}
