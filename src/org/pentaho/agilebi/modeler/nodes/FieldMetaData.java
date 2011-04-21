package org.pentaho.agilebi.modeler.nodes;

import org.pentaho.agilebi.modeler.ModelerMessagesHolder;
import org.pentaho.agilebi.modeler.propforms.FieldsPropertiesForm;
import org.pentaho.agilebi.modeler.propforms.ModelerNodePropertiesForm;
import org.pentaho.metadata.model.concept.types.AggregationType;
import org.pentaho.ui.xul.stereotype.Bindable;

import java.util.Arrays;
import java.util.List;

/**
 * Created: 3/18/11
 *
 * @author rfellows
 */
public class FieldMetaData extends BaseAggregationMetaDataNode {

  private static final String IMAGE = "images/fields.png";
  private static final long serialVersionUID = -7091129923372909756L;
  private CategoryMetaData parent;

  public FieldMetaData( String locale ) {
    super(locale);
  }

  public FieldMetaData(CategoryMetaData parent, String fieldName, String format, String displayName, String locale ) {
    super(fieldName, format, displayName, locale);
    this.parent = parent;
  }

  @Override
  public List<AggregationType> getNumericAggregationTypes() {
    return Arrays.asList(AggregationType.NONE,
        AggregationType.SUM,
        AggregationType.AVERAGE,
        AggregationType.MINIMUM,
        AggregationType.MAXIMUM,
        AggregationType.COUNT,
        AggregationType.COUNT_DISTINCT);
  }


  @Override
  public List<AggregationType> getTextAggregationTypes() {
    return Arrays.asList(AggregationType.NONE, AggregationType.COUNT, AggregationType.COUNT_DISTINCT);
  }

  public CategoryMetaData getParent() {
    return parent;
  }

  public void setParent( CategoryMetaData md ) {
    this.parent = md;
  }

  @Override
  public String getValidImage() {
    return IMAGE;
  }

  @Override
  public Class<? extends ModelerNodePropertiesForm<BaseAggregationMetaDataNode>> getPropertiesForm() {
    return FieldsPropertiesForm.class;
  }

  @Override
  public void validate() {
    valid = true;
    validationMessages.clear();
    // check name
    if (name == null || "".equals(name)) {
      validationMessages.add(ModelerMessagesHolder.getMessages().getString("validation.field.MISSING_NAME"));
      valid = false;
    }
    if (logicalColumn == null) {
      validationMessages.add(ModelerMessagesHolder.getMessages().getString("validation.field.MISSING_BACKING_COLUMN", getName()));
      valid = false;
    }
  }


  @Bindable
  public AggregationType getDefaultAggregation() {
    if (logicalColumn == null) {
      return null;
    }
    if (defaultAggregation == null) {
      switch (logicalColumn.getDataType()) {
        case NUMERIC:
          defaultAggregation = AggregationType.SUM;
          break;
        default:
          defaultAggregation = AggregationType.NONE;
      }
    }
    return defaultAggregation;
  }


  @Override
  public boolean acceptsDrop(Object obj) {
    return false;
  }

}
